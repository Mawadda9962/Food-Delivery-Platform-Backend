package com.example.demo.TRA.Repositories;

import com.example.demo.TRA.Entities.CorporateOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CorporateOrderRepository extends JpaRepository<CorporateOrder ,Integer> {
    @Query("SELECT co FROM CorporateOrder co WHERE co.id = :id AND co.isActive = true")
    List<CorporateOrder> findActiveById(@Param("id") Integer id);
}
