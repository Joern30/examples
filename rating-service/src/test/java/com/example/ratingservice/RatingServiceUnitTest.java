package com.example.ratingservice;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;
import io.restassured.authentication.FormAuthConfig;
import io.restassured.config.RedirectConfig;
import io.restassured.response.Response;

public class RatingServiceUnitTest {
	private final String ROOT_URI = "http://localhost:8080";
	private FormAuthConfig formConfig
	= new FormAuthConfig("/login", "username", "password");
	@Before
	public void setup() {
//	RestAssured.config = config().redirect(
//	RedirectConfig.redirectConfig().followRedirects(false));
	}
	
	@Test
	public void whenGetAllRatings_thenSuccess() {
		Response response = RestAssured.get(ROOT_URI + "/rating-service/ratings/all");
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatusCode());
		Assert.assertNotNull(response.getBody());
	}
}
