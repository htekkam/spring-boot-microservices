package com.techie.microservices.product;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Matches;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.shaded.org.hamcrest.Matchers;



import io.restassured.RestAssured;
import io.restassured.matcher.ResponseAwareMatcher;
import io.restassured.response.Response;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:7.0.5");
	
	@LocalServerPort
	private Integer port;
	
	@BeforeEach
	void setUp() {
		
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}
	
	static {
		mongoDBContainer.start();
	}
	@Test
	void shouldCreateProduct() {
		
		String requestBody = """ 
				{
    "name":"Samsung galaxy",
    "description":"Premium phone from Samsung",
    "price":56000
}
				""";
		
		RestAssured.given()
	    .contentType("application/json")
	    .body(requestBody)
	.when()
	    .post("/api/product")
	.then()
	    .statusCode(201)
	    .body("id", (ResponseAwareMatcher<Response>) Matchers.notNullValue())
	    .body("name", (ResponseAwareMatcher<Response>) Matchers.equalTo("Samsung galaxy"))
	    .body("description", (ResponseAwareMatcher<Response>) Matchers.equalTo("Premium phone from Samsung"))
	    .body("price", (ResponseAwareMatcher<Response>) Matchers.equalTo(56000)); // make sure type matches

	}

}
