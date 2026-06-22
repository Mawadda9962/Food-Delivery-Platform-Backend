package com.example.demo_TRA.Repositories;

import com.example.demo_TRA.Entities.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant,Integer> {

    @Query("SELECT r FROM Restaurant r " + "WHERE LOWER(r.cuisineType) = LOWER(:cuisineType) AND r.isActive = true")
    List<Restaurant> findByCuisineTypeIgnoreCase(@Param("cuisineType") String cuisineType);


    @Query("SELECT r FROM Restaurant r " + "WHERE r.acceptingOrders = true AND r.isActive = true")
    List<Restaurant> findByAcceptingOrdersTrue();

    @Query("SELECT r FROM Restaurant r " + "WHERE r.deliveryFee <= :fee AND r.isActive = true")
    List<Restaurant> findByDeliveryFeeLessThanEqual(@Param("fee") double fee);

    @Query("SELECT r FROM Restaurant r " + "WHERE r.owner.id = :ownerId AND r.isActive = true")
    List<Restaurant> findByOwnerId(@Param("ownerId") Integer ownerId);

    @Query("SELECT r FROM Restaurant r " + "WHERE LOWER(r.name) LIKE LOWER(CONCAT('%', :keyword, '%')) AND r.isActive = true")
    List<Restaurant> searchByNameKeyword(@Param("keyword") String keyword);

}
