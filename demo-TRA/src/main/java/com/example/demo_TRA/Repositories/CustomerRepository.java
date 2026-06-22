package com.example.demo_TRA.Repositories;

import com.example.demo_TRA.Entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    List<Customer> findByEmail(@Param("Email") String email);
}
