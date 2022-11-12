package ru.clevertec.ecl.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ru.clevertec.ecl.util.Identifiable;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tag implements Identifiable<Long> {

    @Id
    @GenericGenerator(
            name = "assigned-identity",
            strategy = "ru.clevertec.ecl.util.AssignedIdentityGenerator"
    )
    @GeneratedValue(
            generator = "tag_id_seq",
            strategy = GenerationType.IDENTITY
    )
    private Long id;
    private String name;
}
