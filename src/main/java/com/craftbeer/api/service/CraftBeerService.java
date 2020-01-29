package com.craftbeer.api.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craftbeer.api.domain.BeerDomain;
import com.craftbeer.api.exception.NotFoundException;
import com.craftbeer.api.exception.UnprocessableEntityException;
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
			throw new NotFoundException();
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
			throw new UnprocessableEntityException("There is a beer registered with this name already.");
		}
		return craftBeerRepository.save(beer.toDomain());
	}
	
	public void updateBeer(BeerRequest beer, String id) {
		craftBeerRepository.findById(id).orElseThrow(NotFoundException::new);
		verifyName(beer.getName());
		craftBeerRepository.save(beer.toDomain(id));
	}
	
	public void partialUpdateBeer(String id, BeerRequest beer) {
		BeerDomain beerDomain = craftBeerRepository.findById(id).orElseThrow(NotFoundException::new);
		BeerRequest newValue = validateFields(beer, beerDomain);
		craftBeerRepository.save(newValue.toDomain(id));
	}
	
	public void deleteBeer(String id) {
		craftBeerRepository.findById(id).orElseThrow(NotFoundException::new);
		craftBeerRepository.deleteById(id);
	}
	
	public void verifyName(String name) {
		Optional<BeerDomain> findByName = craftBeerRepository.findByName(name);
		
		if(findByName.isPresent()) {
			throw new UnprocessableEntityException("There is a beer registered with this name already.");
		}
	}
	
	/**
	 * Method to get the jsonRequest and entity from db and override the entity with the new value
	 * received in jsonRequest
	 * @param beer
	 * @param beerDomain
	 * @return BeerRequest class
	 */
	public BeerRequest validateFields(BeerRequest beer, BeerDomain beerDomain) {
		if(beer.getName() == null) {
			beer.setName(beerDomain.getName());
		}
		if(beer.getAlcoholContent() == null) {
			beer.setAlcoholContent(beerDomain.getAlcoholContent());
		}
		
		if(beer.getCategory() == null) {
			beer.setCategory(beerDomain.getCategory());
		}
		if(beer.getIngredients() == null) {
			beer.setIngredients(beerDomain.getIngredients());
		}
		if(beer.getPrice() == null) {
			beer.setPrice(beerDomain.getPrice());
		}
		return beer;
	}
}
