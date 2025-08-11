package com.sharma.moneymanager.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.sharma.moneymanager.dto.IncomeDTO;
import com.sharma.moneymanager.entity.CategoryEntity;
import com.sharma.moneymanager.entity.IncomeEntity;
import com.sharma.moneymanager.entity.ProfileEntity;
import com.sharma.moneymanager.repository.CategoryRepository;
import com.sharma.moneymanager.repository.IncomeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IncomeService {
	private final CategoryRepository categoryRepository;
	private final IncomeRepository incomeRepository;
	private final ProfileService profileService;
	
	public IncomeDTO addIncome(IncomeDTO dto) {
		ProfileEntity profile = profileService.getCurrentProfile();
		CategoryEntity category =categoryRepository.findById(dto.getCategoryId())
								.orElseThrow(()-> new RuntimeException("Category Not found"));	
		IncomeEntity newIncome = toEntity(dto,profile,category);
		newIncome = incomeRepository.save(newIncome);
		return toDTO(newIncome);
		
	}
	public List<IncomeDTO> getCurrentMonthIncomeForCurrentProfile(){
		ProfileEntity profile = profileService.getCurrentProfile();
		LocalDate now = LocalDate.now();
		LocalDate startDate = now.withDayOfMonth(1);
		LocalDate endDate = now.withDayOfMonth(now.lengthOfMonth());
		List<IncomeEntity>list = incomeRepository.findByProfileIdAndDateBetween(profile.getId(), startDate, endDate);
		return list.stream().map(this::toDTO).toList();
	}
	
	public void deleteIncome(Long incomeId) {
		ProfileEntity profile = profileService.getCurrentProfile();
		IncomeEntity entity = incomeRepository.findById(incomeId)
								.orElseThrow(()->new RuntimeException("Expense not found"));
		if(!entity.getProfile().getId().equals(profile.getId())) {
			throw new RuntimeException("Unauthorized to delete this expense");
		}
		incomeRepository.delete(entity);
		
	}
	
	public List<IncomeDTO> getLatest5IncomeForCurrentUser(){
		ProfileEntity profile = profileService.getCurrentProfile();
		List<IncomeEntity>list = incomeRepository.findTop5ByProfileIdOrderByDateDesc(profile.getId());
		return list.stream().map(this::toDTO).toList();
	}
	public BigDecimal getTotalIncomeForCurrentUser() {
		ProfileEntity profile = profileService.getCurrentProfile();
		BigDecimal total = incomeRepository.findTotalIncomeByProfileId(profile.getId());
		return total!=null?total:BigDecimal.ZERO;
	}
	
	public List<IncomeDTO> filterIncomes(LocalDate startDate , LocalDate endDate , String keyword,Sort sort){
		ProfileEntity profile = profileService.getCurrentProfile();
		List<IncomeEntity> list =incomeRepository.findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(profile.getId(), startDate, endDate, keyword, sort);
		 return list.stream().map(this::toDTO).toList();
	}
	private IncomeEntity toEntity(IncomeDTO  dto  ,ProfileEntity profile, CategoryEntity category) {
		return IncomeEntity.builder()
				.name(dto.getName())
				.icon(dto.getIcon())
				.amount(dto.getAmount())
				.date(dto.getDate())
				.profile(profile)
				.category(category)
				.build();
	}
	private IncomeDTO toDTO(IncomeEntity entity) {
		return  IncomeDTO.builder()
				.id(entity.getId())
				.name(entity.getName())
				.icon(entity.getIcon())
				.amount(entity.getAmount())
				.date(entity.getDate())
				.categoryId(entity.getCategory()!=null?entity.getCategory().getId():null)
				.categoryName(entity.getCategory()!=null?entity.getCategory().getName():null)
				.createdAt(entity.getCreatedAt())
				.updatedAt(entity.getUpdatedAt())
				.build();
	}
}
