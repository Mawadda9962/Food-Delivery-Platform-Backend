package com.example.demo.TRA.Repositories;

import com.example.demo.TRA.Entities.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery,Integer> {

    @Query("SELECT d FROM Delivery d " + "WHERE d.deliveryDriver.id = :driverId AND d.status = :status AND d.isActive = true")
    List<Delivery> findByDeliveryDriverIdAndStatus(@Param("driverId") Integer driverId, @Param("status") String status);


    @Query("SELECT d FROM Delivery d WHERE d.id = :id AND d.isActive = true")
    Optional<Delivery> findActiveById(@Param("id") Integer id);

    @Query("SELECT d FROM Delivery d WHERE d.deliveryDriver.id = :driverId " +
            "AND d.status NOT IN ('DELIVERED', 'CANCELLED') AND d.isActive = true")
    Optional<Delivery> findActiveDeliveryByDriverId(@Param("driverId") Integer driverId);

    @Query("SELECT d FROM Delivery d WHERE d.status = :status AND d.isActive = true")
    List<Delivery> findByStatus(@Param("status") String status);


    @Query(value = "SELECT d.first_name, d.last_name, d.driver_code, " + "COUNT(del.id) as completed_deliveries " + "FROM delivery_driver d " + "JOIN delivery del ON del.delivery_driver_id = d.id " + "WHERE del.status = 'DELIVERED' " + "AND del.is_active = true " + "AND d.is_active = true " + "GROUP BY d.id " + "ORDER BY completed_deliveries DESC", nativeQuery = true)
    List<Map<String, Object>> findDriversLeaderboard();

}
