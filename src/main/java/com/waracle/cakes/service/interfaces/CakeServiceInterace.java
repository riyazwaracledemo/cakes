package com.waracle.cakes.service.interfaces;

import java.util.List;
import java.util.Optional;

import com.waracle.cakes.entity.Cake;

public interface CakeServiceInterace {

	Cake saveCake(Cake cake);

	List<Cake> saveCakes(List<Cake> cakes);

	Optional<List<Cake>> getCakes();

	Optional<Cake> getCakeById(Long id);

	String deleteCake(Long id);

	Cake updateCake(Cake cake);

}
