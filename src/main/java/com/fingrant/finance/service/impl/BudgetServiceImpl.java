package com.fingrant.finance.service.impl;

import com.fingrant.finance.entity.Budget;
import com.fingrant.finance.exception.CustomException;
import com.fingrant.finance.repository.BudgetRepository;
import com.fingrant.finance.service.BudgetService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class BudgetServiceImpl implements BudgetService {


    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    public Budget createBudget(Budget budget) throws CustomException {

        try{
            return budgetRepository.save(budget);

        }catch(DataIntegrityViolationException ex){

            throw new CustomException("Duplicate Budget Name, please use another name","E101");
        }
    }


    @Override
    public Budget getBudgetById(Long id) {
        Optional<Budget> budget = budgetRepository.findById(id);
        if (budget.isPresent()) {
            return budget.get();
        }else{
            throw new NoSuchElementException("No budget found with id " + id);
        }
    }

    @Override
    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    @Override
    public Budget getBudgetByName(String name) {
       Budget budget = budgetRepository.findBudgetByName(name);

       if(budget != null) {
           return budget;
       }else{
           throw new NoSuchElementException("No budget found with name " + name);
       }
    }

    @Override
    public Budget updateBudget(Long id, Budget budget) {

        Optional<Budget> oldBudgetOpt = budgetRepository.findById(id);

        if(oldBudgetOpt.isPresent()){

            Budget oldBudget = oldBudgetOpt.get();

            if(budget.getEquipmentBudget() != null){
                oldBudget.setEquipmentBudget(budget.getEquipmentBudget());
            }
            if(budget.getOtherBudget() != null){
                oldBudget.setOtherBudget(budget.getOtherBudget());
            }
            if(budget.getPersonnelBudget() != null){
                oldBudget.setPersonnelBudget(budget.getPersonnelBudget());
            }
            if(budget.getTravelBudget() != null){
                oldBudget.setTravelBudget(budget.getTravelBudget());
            }
            if(budget.getSuppliesBudget() != null){
                oldBudget.setSuppliesBudget(budget.getSuppliesBudget());
            }
            if(budget.getTotalAmount() != null){
                oldBudget.setTotalAmount(budget.getTotalAmount());
            }
            if(budget.getName() != null){
                oldBudget.setName(budget.getName());
            }
            if(budget.getStartDate() != null){
                oldBudget.setStartDate(budget.getStartDate());
            }
            if(budget.getEndDate() != null){
                oldBudget.setEndDate(budget.getEndDate());
            }

            if(budget.getGrantId() != null){
                oldBudget.setGrantId(budget.getGrantId());
            }

           return budgetRepository.save(oldBudget);
        }else{
            throw new NoSuchElementException("No budget found with id " + id);
        }
    }

    @Override
    public boolean deleteBudget(Long id) {

        Optional<Budget> budgetOptional = budgetRepository.findById(id);

        if(budgetOptional.isPresent()){
            budgetRepository.delete(budgetOptional.get());
            return true;
        }else{
            throw new NoSuchElementException("No budget found with id " + id);
        }

    }
}
