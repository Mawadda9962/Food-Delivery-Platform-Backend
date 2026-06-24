package com.example.demo_TRA.Repositories;

import com.example.demo_TRA.Entities.CorporateOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CorporateOrderRepository {
    @Query("SELECT co FROM CorporateOrder co WHERE co.id = :id AND co.isActive = true")
    List<CorporateOrder> findActiveById(@Param("id") Integer id);
}
