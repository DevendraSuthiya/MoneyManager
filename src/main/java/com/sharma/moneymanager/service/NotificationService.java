package com.sharma.moneymanager.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sharma.moneymanager.dto.ExpenseDTO;
import com.sharma.moneymanager.entity.ProfileEntity;
import com.sharma.moneymanager.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
	
	private final ProfileRepository profileRepository;
	private final EmailService emailService;
	private final ExpenseService expenseService;
	
	@Value("${money.manager.frontend.url}")
	private String frontendUrl;
	
	
	//@Scheduled(cron ="0 * * * * *",zone = "IST")
	@Scheduled(cron ="0 0 22 * * *",zone = "IST")
	public void sendDailyIncomeExpenseRemider() {
		log.info("job Started : sendDailyIncomeExpenseReminder()");
		 
		List<ProfileEntity> profiles = profileRepository.findAll();
		for(ProfileEntity profile : profiles) {
			String body = "Hi " + profile.getFullName() + ",<br><br>"
				    + "This is a friendly reminder to add your income and expenses for today in Money Manager.<br><br>"
				    + "<a href='" + frontendUrl + "' style='display:inline-block;padding:10px 20px;background-color:#4CAF50;color:#fff;text-decoration:none;border-radius:5px;font-weight:bold;'>"
				    + "Go to Money Manager</a>"
				    + "<br><br>Best regards, Good Night,<br>Money Manager Team";
			emailService.sendMimeMail(profile.getEmail(), "Daily Remainder : Add Your income and expenses", body);
			}
		log.info("job Completed : sendDailyIncomeExpenseReminder()");
	}
	
	@Scheduled(cron ="0 0 23 * * *",zone = "IST")
	//@Scheduled(cron ="0 * * * * *",zone = "IST")
	public void sendDailyExpenseSummary() {
		log.info("job Started : sendDailyExpenseSummary()");
		List<ProfileEntity> profiles = profileRepository.findAll();
		for(ProfileEntity profile :profiles) {
			List<ExpenseDTO> expenses  = expenseService.getExpensesForUserOnDate(profile.getId(), LocalDate.now(ZoneId.of("Asia/Kolkata")));
			if (!expenses.isEmpty()) {
			    StringBuilder table = new StringBuilder();

			    // Start table with inline styling for borders
			    table.append("<table style='width: 100%; border-collapse: collapse; font-family: Arial, sans-serif; font-size: 14px; border: 1px solid #ccc;'>")
			         .append("<tr style='background-color: #4CAF50; color: white;'>")
			         .append("<th style='border: 1px solid #ccc; padding: 10px;'>Sr No.</th>")
			         .append("<th style='border: 1px solid #ccc; padding: 10px;'>Name</th>")
			         .append("<th style='border: 1px solid #ccc; padding: 10px;'>Amount</th>")
			         .append("<th style='border: 1px solid #ccc; padding: 10px;'>Category</th>")
			         .append("</tr>");

			    int srNo = 1;
			    for (ExpenseDTO expense : expenses) {
			        String rowBg = (srNo % 2 == 0) ? "#f9f9f9" : "#ffffff";
			        table.append("<tr style='background-color: ").append(rowBg).append(";'>")
			             .append("<td style='border: 1px solid #ccc; padding: 10px;'>").append(srNo++).append("</td>")
			             .append("<td style='border: 1px solid #ccc; padding: 10px;'>").append(expense.getName()).append("</td>")
			             .append("<td style='border: 1px solid #ccc; padding: 10px;'>").append(expense.getAmount()).append("</td>")
			             .append("<td style='border: 1px solid #ccc; padding: 10px;'>").append(expense.getCategoryName()).append("</td>")
			             .append("</tr>");
			    }

			    table.append("</table>");

			    // Full email body
			    String body = "Hi " + profile.getFullName() + ",<br><br>"
			            + "Hope you're doing well! Here's a quick snapshot of your expenses recorded today in Money Manager.<br><br>"
			            + "<b>Today's Expenses:</b><br><br>"
			            + table.toString()
			            +"<br><br>"
			            + "<a href='" + frontendUrl + "' style='display:inline-block;padding:10px 20px;background-color:#4CAF50;color:#fff;text-decoration:none;border-radius:5px;font-weight:bold;'>"
					            + "Go to Money Manager</a><br><br>"
			            + "Stay on top of your finances!<br>Money Manager Team";

			    emailService.sendMimeMail(profile.getEmail(), "Daily Report: Your Expenses for Today", body);
			}

	    }
		log.info("job completed : sendDailyExpenseSummary()");
	}

}
