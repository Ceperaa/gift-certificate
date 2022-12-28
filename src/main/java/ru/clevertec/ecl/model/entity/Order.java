package ru.clevertec.ecl.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createDate;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    private GiftCertificate giftCertificate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    
    @PrePersist
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
    }
}
