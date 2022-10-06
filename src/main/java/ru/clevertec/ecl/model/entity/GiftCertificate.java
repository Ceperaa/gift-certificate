package ru.clevertec.ecl.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.clevertec.ecl.util.LocalDateStringConvert;
import ru.clevertec.ecl.util.StringLocalDateConvert;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EqualsAndHashCode(of = {"name", "description", "duration"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GiftCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;

    @JsonSerialize(converter = LocalDateStringConvert.class)
    @JsonDeserialize(converter = StringLocalDateConvert.class)
    private LocalDateTime createDate;

    @JsonSerialize(converter = LocalDateStringConvert.class)
    @JsonDeserialize(converter = StringLocalDateConvert.class)
    private LocalDateTime lastUpdateDate;

    @ToString.Exclude
    @ManyToMany
    private List<Tag> tag = new ArrayList<>();


    @PreUpdate
    public void setLastUpdateDate() {
        this.lastUpdateDate = LocalDateTime.now();
    }
}
