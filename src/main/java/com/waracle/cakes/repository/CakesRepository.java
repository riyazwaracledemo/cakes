package com.waracle.cakes.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.waracle.cakes.entity.Cake;

public interface CakesRepository extends JpaRepository<Cake, Long> {

}
