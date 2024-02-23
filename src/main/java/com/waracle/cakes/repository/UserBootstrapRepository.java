package com.waracle.cakes.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waracle.cakes.entity.BootstrapUser;

public interface UserBootstrapRepository extends JpaRepository<BootstrapUser, Integer> {

	Optional<BootstrapUser> findByUsername(String username);

}
