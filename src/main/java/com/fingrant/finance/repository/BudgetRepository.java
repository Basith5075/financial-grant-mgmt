package com.fingrant.finance.repository;

import com.fingrant.finance.entity.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BudgetRepository extends JpaRepository<Budget,Long> {

    Budget findBudgetByName(String name);
}
