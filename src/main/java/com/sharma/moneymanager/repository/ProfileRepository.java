package com.sharma.moneymanager.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sharma.moneymanager.entity.ProfileEntity;

@Repository
public interface ProfileRepository extends JpaRepository<ProfileEntity, Long>{
	
	Optional<ProfileEntity>findByEmail(String email);
	Optional<ProfileEntity>findByActivationToken(String activationToken);
}
