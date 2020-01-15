package com.craftbeer.api.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.craftbeer.api.domain.BeerDomain;

@Repository
public interface CraftBeerRepository extends MongoRepository<BeerDomain, String>{
	Optional<BeerDomain> findByName(String name);
}
