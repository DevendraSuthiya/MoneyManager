package com.sharma.moneymanager.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharma.moneymanager.service.DashboardService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/dashboard")
public class DashBoardController {
	
	private final DashboardService dashboardService;
	
	@GetMapping
	public ResponseEntity<Map<String,Object>> getDashboardData(){
		Map<String,Object> dashboardData = dashboardService.getDashboardData();
		return ResponseEntity.ok(dashboardData);
	}
}
