package ru.clevertec.ecl.model.entity;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements Persistable<Long> {

    @Id
    private Long id;
    private String name;
    private String surname;

    @ToString.Exclude
    @OneToMany(mappedBy = "user")
    private List<Order> orders;

    private @Transient boolean isNew = true;

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PrePersist
    @PostLoad
    void markNotNew() {
        this.isNew = false;
    }

}
