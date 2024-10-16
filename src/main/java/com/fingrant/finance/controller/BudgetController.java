package com.fingrant.finance.controller;

import com.fingrant.finance.entity.Budget;
import com.fingrant.finance.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
@RequestMapping("/v1/budget")
public class BudgetController {


    private final BudgetService budgetService;

    public BudgetController(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @PostMapping
    public Budget createBudget(@Valid @RequestBody Budget budget){
        return budgetService.createBudget(budget);
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
