package com.craftbeer.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.craftbeer.api.domain.BeerDomain;
import com.craftbeer.api.json.request.BeerRequest;
import com.craftbeer.api.service.CraftBeerService;
import com.craftbeer.com.json.response.GetBeerResponse;

@RestController
@RequestMapping("/craftbeer")
public class CraftBeerController {

	@Autowired
	CraftBeerService craftBeerService;

	@GetMapping("/beers")
	public ResponseEntity<List<GetBeerResponse>> getBeers() {
		return ResponseEntity.ok(craftBeerService.getAllBeers());
	}
	
	@GetMapping("/beers/{id}")
	public ResponseEntity<GetBeerResponse> getBeerById(
			@PathVariable("id") String id) {
		return ResponseEntity.ok(craftBeerService.getBeerById(id));
	}

	@PostMapping("/beers")
	public ResponseEntity<Void> saveBeer(
			@RequestBody BeerRequest requestBody,
			UriComponentsBuilder b) {
		BeerDomain saveBeer = craftBeerService.saveBeer(requestBody);
		return ResponseEntity.created(b.path("/craftbeer/beers/{id}").buildAndExpand(saveBeer.getId()).toUri()).build();
	}

	@PutMapping("/beers/{id}")
	public ResponseEntity<GetBeerResponse> updateBeer(
			@PathVariable("id") String id,
			@RequestBody BeerRequest requestBody) {
		craftBeerService.updateBeer(requestBody, id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@PatchMapping("/beers/{id}")
	public ResponseEntity<GetBeerResponse> partialUpdateBeer(
			@PathVariable("id") String id,
			@RequestBody BeerRequest requestBody) {
		craftBeerService.partialUpdateBeer(id, requestBody);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@DeleteMapping("/beers/{id}")
	public ResponseEntity<Void> deleteBeer(
			@PathVariable("id") String id) {
		craftBeerService.deleteBeer(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
