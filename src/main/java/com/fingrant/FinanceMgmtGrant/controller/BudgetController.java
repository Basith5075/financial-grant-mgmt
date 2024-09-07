package com.fingrant.FinanceMgmtGrant.controller;

import com.fingrant.FinanceMgmtGrant.entity.Budget;
import com.fingrant.FinanceMgmtGrant.service.BudgetService;
import com.fingrant.FinanceMgmtGrant.service.BulkBudgetUpdates;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Validated
@RequestMapping("/budget")
public class BudgetController {


    @Autowired
    private BudgetService budgetService;

    @PostMapping
    public Budget createBudget(@Valid @RequestBody Budget budget){

        return budgetService.createBuget(budget);
    }

    @GetMapping
    public List<Budget> getAllBudgets(){
        return budgetService.getAllBudgets();
    }
    @GetMapping("/{id}")
    public Budget getBudgetById(@PathVariable Long id ){

        return budgetService.getBudgetById(id);
    }

    @GetMapping("/getBudgetbyname/{name}")
    public Budget getBudgetByName(@PathVariable String name) {

        return budgetService.getBudgetByName(name);
    }


    @PutMapping("/{id}")
    public Budget updateBudget(@PathVariable Long id , @RequestBody Budget budget){

        return budgetService.updateBudget(id,budget);
    }

    @DeleteMapping("/{id}")
    public boolean deleteBudget(@PathVariable Long id){
        return budgetService.deleteBudget(id);
    }


}
