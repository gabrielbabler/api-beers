package com.craftbeer.api.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.craftbeer.com.json.response.GetBeerResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Beers")
public class BeerDomain {
	@Id
	private String id;
	private String name;
	private String ingredients;
	private String alcoholContent;
	private Integer price;
	private String category;
	
	public GetBeerResponse toResponse() {
		return GetBeerResponse.builder()
				.id(id)
				.name(name)
				.ingredients(ingredients)
				.alcoholContent(alcoholContent)
				.price(price)
				.category(category)
				.build();
	}
}
