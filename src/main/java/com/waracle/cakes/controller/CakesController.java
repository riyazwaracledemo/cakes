package com.waracle.cakes.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.waracle.cakes.entity.Cake;
import com.waracle.cakes.service.CakesService;

import jakarta.persistence.EntityNotFoundException;

@RestController
public class CakesController {

	@Autowired
	private CakesService service;

	private static final Logger log = LoggerFactory.getLogger(CakesController.class);

	public CakesController(CakesService service) {
		this.service = service;
	}

	@GetMapping("/cakes")
	public ResponseEntity<List<Cake>> findAllCakes() {
		Optional<List<Cake>> cakes;
		try {
			cakes = service.getCakes();
			if (cakes.isPresent()) {
				return new ResponseEntity<>(cakes.get(), HttpStatus.OK);
			} else {
				log.error("failed to get cakes");
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("exception encountered while getting cakes: {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/cakes/{id}")
	public ResponseEntity<Cake> findCakeById(@PathVariable long id) {
		Optional<Cake> cake;

		cake = service.getCakeById(id);
		if (cake.isPresent()) {
			return new ResponseEntity<>(cake.get(), HttpStatus.OK);
		} else {
			log.error("failed to get cake wih id: {}", id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/addCake")
	public ResponseEntity<Cake> addCake(@RequestBody Cake cake) {
		Optional<Cake> addedCake;

		addedCake = Optional.ofNullable(service.saveCake(cake));
		if (addedCake.isPresent()) {
			return new ResponseEntity<>(addedCake.get(), HttpStatus.OK);
		} else {
			log.error("failed to add cake: {}", cake.title);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/addCakes")
	public ResponseEntity<List<Cake>> addCakes(@RequestBody List<Cake> cakes) {
		Optional<List<Cake>> addedCakes;
		addedCakes = Optional.ofNullable(service.saveCakes(cakes));
		if (addedCakes.isPresent()) {
			return new ResponseEntity<>(addedCakes.get(), HttpStatus.OK);
		} else {
			log.error("failed to add cakes");
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/updateCake")
	public ResponseEntity<Cake> updateCake(@RequestBody Cake cake) {
		Optional<Cake> updatedCake;
		updatedCake = Optional.ofNullable(service.updateCake(cake));
		if (updatedCake.isPresent()) {
			return new ResponseEntity<>(updatedCake.get(), HttpStatus.OK);
		} else {
			log.error("failed to update the cake: {}", cake.title);
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<HttpStatus> deleteCake(@PathVariable Long id) {
		try {
			service.deleteCake(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (EntityNotFoundException e) {
			log.error("could not find cake with id: {} for deletion", id);
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			log.error("exception encountered while deleting cake: {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
