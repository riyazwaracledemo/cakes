package com.waracle.cakes;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.waracle.cakes.entity.BootstrapUser;
import com.waracle.cakes.service.UserBootstrapService;

@Component
public class BootstrapUsers implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	UserBootstrapService userService;

	private static final Logger log = LoggerFactory.getLogger(BootstrapUsers.class);

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		userService.addBootstrapUsers(
				Arrays.asList(BootstrapUser.builder().id(1).username("user1").password("user1").role("USER").build(),
						BootstrapUser.builder().id(1).username("user2").password("user2").role("USER").build(),
						BootstrapUser.builder().id(1).username("user3").password("user3").role("USER").build(),
						BootstrapUser.builder().id(1).username("admin").password("admin").role("ADMIN").build()));

		log.info("adding bootstrap users: 'admin' with role 'admin' and 'user1', 'user2', 'user3' with role 'user'");
	}

}
