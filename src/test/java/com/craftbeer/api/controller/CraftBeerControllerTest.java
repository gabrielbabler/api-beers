package com.craftbeer.api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import org.junit.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.craftbeer.api.domain.BeerDomain;
import com.craftbeer.api.json.request.BeerRequest;
import com.craftbeer.api.service.CraftBeerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CraftBeerControllerTest extends AbstractControllerTest {

	private static final String GET_BEERS = "/craftbeer/beers";
	private static final String GET_BEERS_BY_ID = "/craftbeer/beers/{id}";
	private static final String POST_BEER = "/craftbeer/beers";
	private static final String PUT_BEER = "/craftbeer/beers/{id}";
	
	private String id;
	private BeerRequest request;
	
	@MockBean
	private CraftBeerService craftBeerService;

	@Test
	public void callGetBeersAndExpectSuccess() throws Exception {
		whenCallGetBeers();
		thenExpectSuccess();
	}

	@Test
	public void callGetBeersByIdAndExpectSuccess() throws Exception {
		givenId();
		whenCallGetBeersById();
		thenExpectSuccess();
	}

	@Test
	public void callPostBeerAndExpectSuccess() throws Exception {
		givenBody();
		givenCraftBeerServiceSaveBeerReturnSuccess();
		whenCallPostBeer();
		thenExpectCreated();
	}
	
	@Test
	public void callPutBeerAndExpectSuccess() throws Exception {
		givenBody();
		givenId();
		givenCraftBeerServiceUpdateBeerReturnSuccess();
		whenCallPutBeer();
		thenExpectNoContent();
	}

	// GIVEN
	private void givenId() {
		id = "1";
	}

	private void givenBody() throws JsonProcessingException {
		request = new BeerRequest("a", "a", "a", 1, "a");
	}
	
	private void givenCraftBeerServiceSaveBeerReturnSuccess() {
		BeerDomain beerDomain = BeerDomain.builder()
			.name("a")
			.ingredients("a")
			.alcoholContent("a")
			.price(1)
			.category("a").build();
		
		doReturn(beerDomain).when(craftBeerService).saveBeer(any(BeerRequest.class));
	}
	
	private void givenCraftBeerServiceUpdateBeerReturnSuccess() {
		doNothing().when(craftBeerService).updateBeer(any(BeerRequest.class), anyString());
	}

	// WHEN
	private void whenCallGetBeers() throws Exception {
		response = mockMvc.perform(get(GET_BEERS)).andReturn().getResponse();
	}

	private void whenCallGetBeersById() throws Exception {
		response = mockMvc.perform(get(GET_BEERS_BY_ID, id)).andReturn().getResponse();
	}

	private void whenCallPostBeer() throws Exception {
		response = mockMvc.perform(post(POST_BEER)
				.content(asJsonString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
	}
	
	private void whenCallPutBeer() throws Exception {
		response = mockMvc.perform(put(PUT_BEER, id)
				.content(asJsonString(request))
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn().getResponse();
	}

	// THEN
	private void thenExpectSuccess() {
		assertThat(HttpStatus.OK.value()).isEqualTo(response.getStatus());
	}

	private void thenExpectCreated() {
		assertThat(HttpStatus.CREATED.value()).isEqualTo(response.getStatus());
	}
	
	private void thenExpectNoContent() {
		assertThat(HttpStatus.NO_CONTENT.value()).isEqualTo(response.getStatus());
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
