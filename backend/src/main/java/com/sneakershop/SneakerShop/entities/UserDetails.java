package com.sneakershop.SneakerShop.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_details")
public class UserDetails {

    @Id
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "privacy_agreement", nullable = false)
    private Boolean privacyAgreement;

    @Column(name = "ad_agreement")
    private Boolean adAgreement;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "register_date")
    @CreatedDate
    private LocalDate registerDate;

    @Column(name = "discount", precision = 4, scale = 2, nullable = false)
    private BigDecimal discount;

    @Column(name = "total_redemption", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalRedemption;

    @Column(name = "total_refund", precision = 10, scale = 2, nullable = false)
    private BigDecimal totalRefund;
}
