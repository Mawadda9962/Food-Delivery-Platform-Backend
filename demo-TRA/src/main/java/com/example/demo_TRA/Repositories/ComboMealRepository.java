package com.example.demo_TRA.Repositories;

import com.example.demo_TRA.Entities.ComboMeal;
import org.springframework.boot.jackson.JacksonComponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComboMealRepository extends JpaRepository<ComboMeal,Integer> {

    @Query("SELECT c FROM ComboMeal c " + "WHERE c.restaurant.id = :restaurantId AND c.isActive = true")
    List<ComboMeal> findByRestaurantId(@Param("restaurantId") Integer restaurantId);

    @Query("SELECT c FROM ComboMeal c " + "JOIN c.menuItems m " + "WHERE m.id = :menuItemId AND c.isActive = true")
    List<ComboMeal> findCombosContainingMenuItem(@Param("menuItemId") Integer menuItemId);
}
