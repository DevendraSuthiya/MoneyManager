package com.sharma.moneymanager.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sharma.moneymanager.entity.ProfileEntity;
import com.sharma.moneymanager.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
	
	private final ProfileRepository profileRepository;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		ProfileEntity existingProfile = profileRepository.findByEmail(email)
				.orElseThrow(()->new UsernameNotFoundException("Profile not found "+email));
		return User.builder()
				.username(existingProfile.getEmail())
				.password(existingProfile.getPassword())
				.authorities(Collections.emptyList())
				.build();
				
	}

}
