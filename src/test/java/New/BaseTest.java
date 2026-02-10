package New;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BaseTest {

	String accessToken;

	UserDataGenerator userDataGenerator = new UserDataGenerator();


	@BeforeEach
	public void setUp() {
		RestAssured.baseURI = ("https://stellarburgers.education-services.ru/");
	}


	@AfterEach
	public void cleanUpUser() {
		if (accessToken != null) {
			{
				given()
						.header("Content-type", "application/json")
						.header("Authorization", accessToken)
						.log().all()
						.when()
						.delete("api/auth/user")
						.then()
						.statusCode(202)
						.log().all()
						.assertThat().body("success", equalTo(true));
			}
		}
	}
}