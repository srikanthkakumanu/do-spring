//package books;
//
//import com.jayway.jsonpath.DocumentContext;
//import com.jayway.jsonpath.JsonPath;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//// This will start our Spring Boot application
//// and make it available for our test to perform requests to it.
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//class CashCardApplicationTests {
//
//	// Injecting a test helper that'll allow us to make HTTP requests to the locally running application
//	@Autowired
//	TestRestTemplate restTemplate;
//
//	@Test
//	void shouldReturnCashCardWhenDataIsSaved() {
//		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/99", String.class);
//
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//		// This converts the response string into a JSON-Aware object with lots of helper methods
//		DocumentContext documentContext = JsonPath.parse(response.getBody());
//
//		Number id = documentContext.read("$.id");
//
//		assertThat(id).isEqualTo(99);
//
//		Double amount = documentContext.read("$.amount");
//
//		assertThat(amount).isEqualTo(123.45);
//	}
//
//	@Test
//	void shouldNotReturnACashCardWithAnUnknownId() {
//
//		ResponseEntity<String> response = restTemplate.getForEntity("/cashcards/1000", String.class);
//
//		assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
//
//		assertThat(response.getBody()).isBlank();
//	}
//
//	@Test
//	void contextLoads() {
//	}
//
//}
