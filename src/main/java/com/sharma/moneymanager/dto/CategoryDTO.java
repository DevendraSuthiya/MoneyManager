package com.sharma.moneymanager.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDTO {
	 private Long id;
	  private String name;
	  private String icon;
	  private LocalDateTime createdAt;

	  private LocalDateTime updatedAt;
	  private String type;
	 
	  private Long profileId;
}
