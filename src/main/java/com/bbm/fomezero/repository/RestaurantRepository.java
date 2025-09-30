package com.bbm.fomezero.repository;

import com.bbm.fomezero.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r WHERE lower(r.name) LIKE lower(concat('%', :keyword, '%'))" +
            "OR lower(r.cuisineType) LIKE lower(concat('%', :keyword, '%'))" +
            "OR lower(r.description) LIKE lower(concat('%', :keyword, '%'))" +
            "OR lower(r.address.city) LIKE lower(concat('%',:keyword, '%'))" +
            "OR lower(r.address.province) LIKE lower(concat('%',:keyword, '%'))")
    List<Restaurant> searchRestaurant(String keyword);

    Restaurant findByOwnerId(Long id);
}
