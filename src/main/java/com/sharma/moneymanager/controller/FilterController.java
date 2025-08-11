package com.sharma.moneymanager.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharma.moneymanager.dto.ExpenseDTO;
import com.sharma.moneymanager.dto.FilterDTO;
import com.sharma.moneymanager.dto.IncomeDTO;
import com.sharma.moneymanager.service.ExpenseService;
import com.sharma.moneymanager.service.IncomeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/filter")
public class FilterController {
	private final IncomeService incomeService;
	private final ExpenseService expenseService;
	
	@PostMapping
	public ResponseEntity<?> filterTransactions(@RequestBody FilterDTO filter){
	   LocalDate startDate = filter.getStartDate()!=null?filter.getStartDate():LocalDate.now();
	   LocalDate endDate = filter.getEndDate()!=null?filter.getEndDate():LocalDate.now();
	   String keyword =filter.getKeyword()!=null?filter.getKeyword():"";
	   String sortField = filter.getSortField()!=null?filter.getSortField():"date";
	   Sort.Direction direction = "desc".equalsIgnoreCase(filter.getSortOrder())?Sort.Direction.DESC:Sort.Direction.ASC;
	   Sort sort = Sort.by(direction,sortField);
	   
	   if("income".equalsIgnoreCase(filter.getType())) {
		   List<IncomeDTO> incomes = incomeService.filterIncomes(startDate, endDate, keyword, sort);
		   return ResponseEntity.ok(incomes);
	   }
	   else if("expense".equalsIgnoreCase(filter.getType())) {
		   List<ExpenseDTO> expense = expenseService.filterExpenses(startDate, endDate, keyword, sort);
		   return ResponseEntity.ok(expense);
	   }
	   else {
		   return ResponseEntity.badRequest().body("Invalid type. Must be 'Income' or 'Expense'");
	   }
	
	}
}
