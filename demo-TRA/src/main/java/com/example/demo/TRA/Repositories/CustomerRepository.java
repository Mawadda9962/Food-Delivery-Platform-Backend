package com.example.demo.TRA.Repositories;

import com.example.demo.TRA.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.customerEmail = :email AND c.isActive = true")
    Optional<Customer> findByEmail(@Param("email") String email);

    @Query("SELECT c FROM Customer c " + "WHERE c.loyaltyPoints >= :points AND c.isActive = true")
    List<Customer> findByLoyaltyPointsGreaterThanEqual(@Param("points") int points);


    @Query("SELECT c FROM Customer c WHERE c.createDate BETWEEN :start AND :end AND c.isActive = true")
    List<Customer> findCustomersRegisteredBetween(@Param("start") LocalDate start, @Param("end") LocalDate end);

    //deactivate Customer by id
    @Query("SELECT c FROM Customer c WHERE c.id = :id AND c.isActive = true")
    Optional<Customer> findActiveById(@Param("id") Integer id);

    @Query("SELECT c FROM Customer c WHERE c.isActive = true")
    List<Customer> findAllActiveCustomers();
}
