package com.waracle.cakes.config;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.waracle.cakes.entity.BootstrapUser;
import com.waracle.cakes.repository.UserBootstrapRepository;

@Component
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserBootstrapRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<BootstrapUser> user = repository.findByUsername(username);
		return user.map(CustomUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

	}

}
