package com.uber.user.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;

import java.time.LocalDateTime;

@Entity
public class BlacklistedToken {

    @Id
    private String id;   // UUID

    @Column(length = 500, nullable = false)
    private String token;

    private LocalDateTime blacklistedAt;

    @PrePersist
    public void generateId() {
        if (id == null) {
            this.id = java.util.UUID.randomUUID().toString();
        }
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getBlacklistedAt() {
        return blacklistedAt;
    }
    public void setBlacklistedAt(LocalDateTime blacklistedAt) {
        this.blacklistedAt = blacklistedAt;
    }
}
