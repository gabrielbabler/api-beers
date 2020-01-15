package com.craftbeer.com.json.response;

import lombok.Builder;
import lombok.Data;

import org.bson.types.ObjectId;

import lombok.AllArgsConstructor;
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
