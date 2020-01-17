package com.craftbeer.api.json.request;

import com.craftbeer.api.domain.BeerDomain;
import com.craftbeer.com.json.response.GetBeerResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BeerRequest {
	private String name;
	private String ingredients;
	private String alcoholContent;
	private Integer price;
	private String category;
	
	public BeerDomain toDomain() {
		return BeerDomain.builder()
				.name(name)
				.ingredients(ingredients)
				.alcoholContent(alcoholContent)
				.price(price)
				.category(category)
				.build();
	}
	public BeerDomain toDomain(String id) {
		return BeerDomain.builder()
				.id(id)
				.name(name)
				.ingredients(ingredients)
				.alcoholContent(alcoholContent)
				.price(price)
				.category(category)
				.build();
	}
}
