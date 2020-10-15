package com.vinci.livraison.app.security.jwt.entity;


import com.vinci.livraison.app.security.authentications.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Getter
@NoArgsConstructor
public class Token extends AbstractPersistable<UUID> {

    @Id
    @GeneratedValue
    @Column(length = 16, columnDefinition = "BINARY(16)")
    UUID id;

    Long userId;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    UserType userType;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false, updatable = false)
    LocalDateTime createdAt;

    @Column(columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP", nullable = false)
    LocalDateTime updatedAt;

    boolean revoked;

    public Token(Long userId, UserType userType) {
        this.userId = userId;
        this.userType = userType;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void refresh() {
        if (createdAt != null)
            updatedAt = LocalDateTime.now();
    }

    public void revoke() {
        if (!revoked)
            revoked = true;
    }

    @Override
    public boolean isNew() {
        return super.isNew();
    }
}