package ru.clevertec.ecl.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@DynamicUpdate
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ToString.Exclude
    @ManyToMany(mappedBy = "tags",fetch = FetchType.LAZY)
    private List<GiftCertificate> giftCertificateList;
}
