package com.waracle.cakes.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.waracle.cakes.dto.CakeDTO;
import com.waracle.cakes.entity.Cake;
import com.waracle.cakes.service.CakesService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

@Validated
@RestController
public class CakesController {

	@Autowired
	private CakesService service;

	@Autowired
	private ModelMapper modelMapper;

	private static final Logger log = LoggerFactory.getLogger(CakesController.class);

	public CakesController(CakesService service) {
		this.service = service;
	}

	@GetMapping("/cakes")
	public ResponseEntity<List<CakeDTO>> findAllCakes() {
		Optional<List<Cake>> cakes;
		try {
			cakes = service.getCakes();
			if (cakes.isPresent()) {
				List<CakeDTO> returnCakeDTOs = cakes.get().stream().map(cake -> CakeDTO.builder().id(cake.id)
						.title(cake.title).description(cake.description).image(cake.image).build())
						.collect(Collectors.toList());
				return new ResponseEntity<>(returnCakeDTOs, HttpStatus.OK);
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
	public ResponseEntity<CakeDTO> findCakeById(@Valid @PathVariable long id) {
		Optional<Cake> cake;
		try {
			cake = service.getCakeById(id);
			if (cake.isPresent()) {
				CakeDTO returnedCakeDTO = modelMapper.map(cake.get(), CakeDTO.class);
				return new ResponseEntity<>(returnedCakeDTO, HttpStatus.OK);
			} else {
				log.error("failed to get cake wih id: {}", id);
				return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			log.error("exception encountered while getting cake: {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/addCake")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<CakeDTO> addCake(@Valid @RequestBody CakeDTO cakeDTO) {
		Optional<Cake> addedCake;
		try {
			Cake cake = modelMapper.map(cakeDTO, Cake.class);
			addedCake = Optional.ofNullable(service.saveCake(cake));
			if (addedCake.isPresent()) {
				CakeDTO returnedCakeDTO = modelMapper.map(addedCake.get(), CakeDTO.class);
				return new ResponseEntity<>(returnedCakeDTO, HttpStatus.OK);
			} else {
				log.error("failed to add cake: {}", cake.title);
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("exception encountered while adding cake: {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/addCakes")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<List<CakeDTO>> addCakes(@Valid @RequestBody List<CakeDTO> cakeDTOs) {
		Optional<List<Cake>> addedCakes;
		try {
			List<Cake> cakes = cakeDTOs.stream().map(cakeDTO -> Cake.builder().title(cakeDTO.title)
					.description(cakeDTO.description).image(cakeDTO.image).build()).collect(Collectors.toList());
			addedCakes = Optional.ofNullable(service.saveCakes(cakes));
			if (addedCakes.isPresent()) {
				List<CakeDTO> returnCakeDTOs = addedCakes.get().stream().map(cake -> CakeDTO.builder().id(cake.id)
						.title(cake.title).description(cake.description).image(cake.image).build())
						.collect(Collectors.toList());
				return new ResponseEntity<>(returnCakeDTOs, HttpStatus.OK);
			} else {
				log.error("failed to add cakes");
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("exception encountered while adding cakes: {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/updateCake")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<CakeDTO> updateCake(@RequestBody CakeDTO cakeDTO) {
		Optional<Cake> updatedCake;
		try {
			Cake cake = modelMapper.map(cakeDTO, Cake.class);
			updatedCake = Optional.ofNullable(service.updateCake(cake));
			if (updatedCake.isPresent()) {
				CakeDTO returnedCakeDTO = modelMapper.map(updatedCake.get(), CakeDTO.class);
				return new ResponseEntity<>(returnedCakeDTO, HttpStatus.OK);
			} else {
				log.error("failed to update the cake: {}", cake.title);
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			log.error("exception encountered while updating cake: {}", e.getMessage());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/delete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")
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
