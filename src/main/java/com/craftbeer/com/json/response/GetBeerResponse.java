package com.craftbeer.com.json.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetBeerResponse {
	private String id;
	private String name;
	private String ingredients;
	private String alcoholContent;
	private Integer price;
	private String category;
}
