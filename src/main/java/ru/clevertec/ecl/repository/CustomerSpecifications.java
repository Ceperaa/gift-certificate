package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.ecl.model.entity.GiftCertificate;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class CustomerSpecifications {

    public static Specification<GiftCertificate> byMultipleTags(List<String> tags) {
        AtomicReference<Specification<GiftCertificate>> specification = new AtomicReference<>(Specification.where(null));
        tags
                .stream()
                .<Specification<GiftCertificate>>map(tag -> (root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.join("tag").get("name"), tag))
                .map(spec -> specification.getAndSet(specification.get().and(spec)))
                .collect(Collectors.toList());
        return specification.get();
    }

}
