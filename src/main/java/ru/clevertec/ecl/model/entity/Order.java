package ru.clevertec.ecl.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Immutable
@Entity
@Table(name = "orders")
public class Order implements Persistable<Long> {

    @Id
    private Long id;
    private LocalDateTime createDate;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    private GiftCertificate giftCertificate;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private @Transient boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    public void setCreateDate() {
        this.createDate = LocalDateTime.now();
        this.isNew = false;
    }
}
