package com.sharma.moneymanager.controller;

import java.io.IOException;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharma.moneymanager.entity.ProfileEntity;
import com.sharma.moneymanager.service.EmailService;
import com.sharma.moneymanager.service.ExcelService;
import com.sharma.moneymanager.service.ExpenseService;
import com.sharma.moneymanager.service.IncomeService;
import com.sharma.moneymanager.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/email")
@RequiredArgsConstructor
public class EmailController {
	private final ExcelService excelService;
	private final IncomeService incomeService;
	private final ExpenseService expenseService;
	private final ProfileService profileService;
	private final EmailService emailService;
	
	@GetMapping("/income-excel")
	public ResponseEntity<Void> emailIncomeExcel() throws IOException{
		ProfileEntity profile = profileService.getCurrentProfile();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		excelService.writeIncomesToExcel(baos,incomeService.getCurrentMonthIncomeForCurrentProfile());
		emailService.sendEmailWithAttachment(profile.getEmail(),
				"Your Income ExcelSheet", "Please Find Attachments", baos.toByteArray(),"income.xlsx");
		return ResponseEntity.ok(null);

	}
	@GetMapping("/expense-excel")
	public ResponseEntity<Void> emailExpenseExcel() throws IOException{
		ProfileEntity profile = profileService.getCurrentProfile();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		excelService.writeExpensesToExcel(baos,expenseService.getCurrentMonthExpenseForCurrentProfile());
		emailService.sendEmailWithAttachment(profile.getEmail(),
				"Your Income ExcelSheet", "Please Find Attachments", baos.toByteArray(),"income.xlsx");
		return ResponseEntity.ok(null);

	}
}
