package com.vinci.livraison.app.security.jwt;

import com.vinci.livraison.app.security.authentications.UserType;
import com.vinci.livraison.app.security.exceptions.InvalidTokenException;
import com.vinci.livraison.app.security.jwt.entity.Token;
import com.vinci.livraison.app.security.jwt.repository.TokenRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Transactional
public class JwtService {

    final JwtConfig config;
    final TokenRepository token$;

    public Optional<Token> getTokenById(String uuid) {
        try {
            UUID id = UUID.fromString(uuid);
            return token$.findById(id)
                    .filter(t -> !t.isRevoked() && isNotExpired(t));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    private  <T> T peek(T object){
        System.out.println("object = " + object);
        return object;
    }

    public String getTokenFromRequest(HttpServletRequest request) {

        return Optional.ofNullable(request.getHeader("Authorization"))
                .filter(t -> t.startsWith(config.getHeaderPrefix()))
                .map(t -> t.substring(7)).orElse(null);
    }

    public JwtTokenResponse generateTokens(Long userId, UserType userType) {

        Token token = token$.save(new Token(userId, userType));

        long expMilisec = toMilliseconde(token.getUpdatedAt()) * 1000 + config.getAccessTokenExpTime(TimeUnit.MILLISECONDS);
        Date expDate = new Date(expMilisec);
        String tokenId = token.getId().toString();


        String a_token = getJwtBuilder()
                .claim("type", "access")
                .setExpiration(expDate)
                .compact();

        String r_token = getJwtBuilder()
                .claim("type", "refresh")
                .setId(tokenId)
                .compact();

        return new JwtTokenResponse(a_token, r_token);

    }

    public AccessTokenResponse generateNewAccessToken(Token token) {

        token.refresh();
        token$.save(token);
        String tokenId = token.getId().toString();
        Long expTimeSec = toMilliseconde(token.getUpdatedAt()) + config.getAccessTokenExpTime(TimeUnit.MILLISECONDS);

        Date expDate = new Date(expTimeSec);

        String tokenStr = getJwtBuilder()
                .claim("type", "access")
                .setExpiration(expDate)
                .setId(tokenId)
                .compact();

        return new AccessTokenResponse(tokenStr);

    }

    public void revokeToken(Token token) {
        token.revoke();
        token$.save(token);
    }

    public Claims getClaimsFromToken(String token) throws InvalidTokenException {

        System.out.println("token = " + token);
        try {
            return getJwtParser().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token Expired");
        } catch (RuntimeException e) {
            System.out.println("e = " + e);
            throw new InvalidTokenException("Token Invalid");
        }

    }

    public Token parseAccessTokenOrfails(String token) throws InvalidTokenException {
        return Optional.of(getClaimsFromToken(token))
                .filter(this::isAccessToken)
                .map(Claims::getId)
                .flatMap(this::getTokenById)
                .filter(this::isNotExpired)
                .orElseThrow(() -> new InvalidTokenException("Token invalid"));
    }

    public Token parseRefreshTokenOrfails(String token) throws InvalidTokenException {
        return Optional.of(getClaimsFromToken(token))
                .filter(this::isRefreshToken)
                .map(Claims::getId)
                .flatMap(this::getTokenById)
                .orElseThrow(() -> new InvalidTokenException("Token invalid"));
    }

    private JwtBuilder getJwtBuilder() {
        return Jwts.builder()
                .signWith(config.getSignatureAlgorithm(), config.getTokenBase64SigningKey());
    }

    private JwtParser getJwtParser() {
        return Jwts.parser()
                .setSigningKey(config.getTokenBase64SigningKey());
    }

    private Long toMilliseconde(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault())
                .toEpochSecond() * 1000;
    }

    public boolean isAccessToken(Claims claims) {
        return "access".equals(claims.get("type", String.class));
    }

    public boolean isRefreshToken(Claims claims) {
        String type = claims.get("type").toString();
        System.out.println("type = " + type);
        return type.equals("refresh");
    }

    public boolean isNotExpired(Token token) {
        System.out.println("token = " + token);
        return token.getUpdatedAt().isAfter(LocalDateTime.now().minusMinutes(config.getRefreshTokenExpTime()));
    }

    public void cleanDbFromRevokedAndExpiredTokens() {
        LocalDateTime dateTime = LocalDateTime.now().minusMinutes(config.getRefreshTokenExpTime());

        System.out.println("exucute clean token db [" + dateTime + "]");
        token$.deleteAllByRevokedTrueOrUpdatedAtIsBefore(dateTime);

    }
}
