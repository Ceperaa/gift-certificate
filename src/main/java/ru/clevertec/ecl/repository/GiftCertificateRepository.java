package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.ecl.model.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long> {

    @Query(" select gc " +
            " from GiftCertificate gc" +
            " left outer join gc.tag t " +
            " where lower(t.name) like lower(concat('%', :name,'%' ))")
    List<GiftCertificate> findByNameWithPagination(@Param("name") String name, Pageable pageable);
}
