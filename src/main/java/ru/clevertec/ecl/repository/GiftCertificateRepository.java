package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.model.entity.GiftCertificate;
import ru.clevertec.ecl.model.entity.GiftCertificate_;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>, JpaSpecificationExecutor<GiftCertificate> {

    @Query(" select gc " +
            " from GiftCertificate gc" +
            " left outer join gc.tag t " +
            " where lower(t.name) like lower(concat('%', :nameTag,'%' )) ")
    List<GiftCertificate> findByName(String nameTag,
                                     Pageable pageable);


    @Override
    @EntityGraph(attributePaths = GiftCertificate_.TAG)
    Optional<GiftCertificate> findById(Long id);

    @Override
    @EntityGraph(attributePaths = {"tag"})
    Page<GiftCertificate> findAll(Example example, Pageable page);
}
