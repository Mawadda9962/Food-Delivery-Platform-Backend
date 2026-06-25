package com.example.demo.TRA.Repositories;

import com.example.demo.TRA.Entities.RestaurantOwner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantOwnerRepository {
    @Query("SELECT o FROM RestaurantOwner o WHERE o.id = :id AND o.isActive = true")
    List<RestaurantOwner> findActiveById(@Param("id") Integer id);

    @Query("SELECT o FROM RestaurantOwner o WHERE o.email = :email AND o.isActive = true")
    List<RestaurantOwner> findByEmail(@Param("email") String email);

    @Query("SELECT o FROM RestaurantOwner o WHERE o.isActive = true")
    List<RestaurantOwner> findAllActive();
}
