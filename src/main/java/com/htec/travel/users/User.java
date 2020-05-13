package com.htec.travel.users;

import com.htec.travel.data.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "user", indexes = {
        @Index(name = "username_index", columnList = "username", unique = true)
})
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 20)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 35)
    private String lastName;

    @Column(nullable = false, length = 30, unique = true)
    private String username;

    @Column(nullable = false, length = 70)
    private String password;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(length = 16, nullable = false)
    private byte[] salt;
}
