package com.sneakershop.SneakerShop.entities;

import com.sneakershop.SneakerShop.core.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @OneToOne
    @JoinColumn(name = "details_id")
    private UserDetails details;

    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;
}
