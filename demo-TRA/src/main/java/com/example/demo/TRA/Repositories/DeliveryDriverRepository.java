package com.example.demo.TRA.Repositories;

import com.example.demo.TRA.Entities.DeliveryDriver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeliveryDriverRepository extends JpaRepository<DeliveryDriver,Integer> {

    @Query("SELECT dd FROM DeliveryDriver dd WHERE dd.id = :id AND dd.isActive = true")
    Optional<DeliveryDriver> findActiveById(@Param("id") Integer id);

    @Query("SELECT dd FROM DeliveryDriver dd WHERE dd.isOnline = true AND dd.isActive = true")
    List<DeliveryDriver> findOnlineDrivers();

    @Query("SELECT dd FROM DeliveryDriver dd WHERE dd.isActive = true")
    List<DeliveryDriver> findAllActiveDrivers();
}
