package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.model.dto.UserMaxSaleDto;
import ru.clevertec.ecl.model.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "orders")
    Optional<User> findById(Long id);

    @Query(nativeQuery = true, value =
            " select price.id,price.name,price.surname,price.sumPrice,tag.countTag " +
            " from (select u.id, u.name, u.surname, sum(o.price) sumPrice " +
            "      from orders o " +
            "               join users u on o.user_id = u.id " +
            "      group by u.id " +
            "      order by sumPrice desc " +
            "     ) price " +
            "         join (select u.id, count(u.id) countTag " +
            "               from orders o " +
            "                        join users u on o.user_id = u.id " +
            "                        join gift_certificate_tag gct on o.gift_certificate_id = gct.gift_certificate_id " +
            "               group by gct.tag_id, u.id " +
            "               order by countTag desc " +
            " ) tag on price.id = tag.id " +
            " limit 1")
    UserMaxSaleDto findByMaxSaleAngMaxUseTag();

}
