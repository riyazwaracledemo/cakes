package com.waracle.cakes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.waracle.cakes.entity.Cake;
import com.waracle.cakes.repository.CakesRepository;
import com.waracle.cakes.service.interfaces.CakeServiceInterace;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CakesService implements CakeServiceInterace {

	@Autowired
	private CakesRepository repo;

	public Cake saveCake(Cake cake) {
		return repo.save(cake);
	}

	public List<Cake> saveCakes(List<Cake> cakes) {
		return repo.saveAll(cakes);
	}

	public Optional<List<Cake>> getCakes() {
		return Optional.of(repo.findAll());
	}

	public Optional<Cake> getCakeById(Long id) {
		return Optional.ofNullable(repo.findById(id).orElseThrow(EntityNotFoundException::new));
	}

	public String deleteCake(Long id) {
		repo.deleteById(id);
		return "Cake with id: " + id + " removed !! ";
	}

	public Cake updateCake(Cake cake) {
		Cake existingCake = repo.findById(cake.getId()).orElse(null);
		existingCake.setTitle(cake.getTitle());
		existingCake.setDescription(cake.getDescription());
		existingCake.setImage(cake.getImage());
		return repo.save(existingCake);
	}
}
