package com.example.demo_TRA.Repositories;

import com.example.demo_TRA.Entities.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Integer> {
    @Query("SELECT ca FROM CustomerAddress ca " + "WHERE ca.city = :city AND ca.isActive = true")
    List<CustomerAddress> findByCity(@Param("city") String city);

    @Query("SELECT ca FROM CustomerAddress ca " + "WHERE ca.customer.id = :customerId " + "AND ca.isActive = true")
    List<CustomerAddress> findByCustomerId(@Param("customerId") Integer customerId);

    @Query("SELECT ca FROM CustomerAddress ca WHERE ca.id = :id AND ca.isActive = true")
    Optional<CustomerAddress> findActiveById(@Param("id") Integer id);
}
