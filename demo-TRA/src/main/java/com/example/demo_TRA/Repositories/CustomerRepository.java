package com.example.demo_TRA.Repositories;

import com.example.demo_TRA.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c" + " WHERE c.email = :email AND c.isActive = true")
    List<Customer> findByEmail(@Param("Email") String email);


    @Query("SELECT c FROM Customer c " + "WHERE c.loyaltyPoints >= :points AND c.isActive = true")
    List<Customer> findByLoyaltyPointsGreaterThanEqual(@Param("points") int points);


    @Query("SELECT c FROM Customer c " + "WHERE c.createdDate BETWEEN :start AND :end AND c.isActive = true")
    List<Customer> findCustomersRegisteredBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

}
