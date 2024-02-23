package com.waracle.cakes.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.waracle.cakes.entity.BootstrapUser;
import com.waracle.cakes.repository.UserBootstrapRepository;

@Service
public class UserBootstrapService {

	@Autowired
	UserBootstrapRepository repository;

	@Autowired
	PasswordEncoder passwordEncoder;

	public List<BootstrapUser> addBootstrapUsers(List<BootstrapUser> users) {
		List<BootstrapUser> updatedUsers = users.stream()
				.map(user -> BootstrapUser.builder().username(user.getUsername())
						.password(passwordEncoder.encode(user.getPassword())).role(user.getRole()).build())
				.collect(Collectors.toList());

		return repository.saveAll(updatedUsers);
	}
}
