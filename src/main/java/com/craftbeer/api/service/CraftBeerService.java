package com.craftbeer.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craftbeer.api.domain.BeerDomain;
import com.craftbeer.api.json.request.BeerRequest;
import com.craftbeer.api.repository.CraftBeerRepository;
import com.craftbeer.com.json.response.GetBeerResponse;

@Service
public class CraftBeerService {

	@Autowired CraftBeerRepository craftBeerRepository;
	
	public List<GetBeerResponse> getAllBeers() {
		return craftBeerRepository.findAll().stream()
				.map(BeerDomain::toResponse)
				.collect(Collectors.toList());
	}
	
	public GetBeerResponse getBeerById(String id) {
		Optional<BeerDomain> findById = craftBeerRepository.findById(id);
		if(!findById.isPresent()) {
			throw new RuntimeException();
		}
		return GetBeerResponse.builder()
				.id(findById.get().getId())
				.alcoholContent(findById.get().getAlcoholContent())
				.name(findById.get().getName())
				.ingredients(findById.get().getIngredients())
				.price(findById.get().getPrice())
				.category(findById.get().getCategory())
				.build();
	}
	
	public BeerDomain saveBeer(BeerRequest beer) {
		Optional<BeerDomain> findByName = craftBeerRepository.findByName(beer.getName());
		if(findByName.isPresent()) {
			throw new RuntimeException();
		}
		return craftBeerRepository.save(beer.toDomain());
	}
	
	public void updateBeer(BeerRequest beer, String id) {
		craftBeerRepository.findById(id).orElseThrow(RuntimeException::new);
		saveBeer(beer);
	}
	
	public void deleteBeer(String id) {
		
	}
}
