package com.user.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "user_profiles")
@Data
public class UserProfile {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = true)
    private String profilePicture;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}

