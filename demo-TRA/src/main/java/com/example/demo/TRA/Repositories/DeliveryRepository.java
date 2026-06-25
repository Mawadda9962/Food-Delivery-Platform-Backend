package com.example.demo.TRA.Repositories;

import com.example.demo.TRA.Entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {

    @Query("SELECT d FROM Delivery d " + "WHERE d.deliveryDriver.id = :driverId AND d.status = :status AND d.isActive = true")
    List<Delivery> findByDeliveryDriverIdAndStatus(@Param("driverId") Integer driverId, @Param("status") String status);


    @Query("SELECT d FROM Delivery d WHERE d.id = :id AND d.isActive = true")
    Optional<Delivery> findActiveById(@Param("id") Integer id);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryDriver.id = :driverId " +
            "AND d.status NOT IN ('DELIVERED', 'CANCELLED') AND d.isActive = true")
    Optional<Delivery> findActiveDeliveryByDriverId(@Param("driverId") Integer driverId);
}
