package com.kayipesyaUser.model;

import com.kayipesyaUser.constant.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Entity(name = "USER")
@Data
@Table(name = "users")
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false,columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(unique = true)
    private String email;
    private String password;
    private String username;
    @Column(updatable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp createdDate;
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp updatedDate;
    private LocalDateTime lastLoginDate;
    @Column(name = "verification_code", length = 64)
    private String verificationCode;
    private boolean enabled;
    private UserRole userRole;


}
