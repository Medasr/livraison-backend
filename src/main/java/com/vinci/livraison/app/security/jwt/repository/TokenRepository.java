package com.vinci.livraison.app.security.jwt.repository;

import com.vinci.livraison.app.security.jwt.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Token WHERE revoked = true OR updatedAt < ?1")
    void deleteAllByRevokedTrueOrUpdatedAtIsBefore(LocalDateTime dateTime);

}
