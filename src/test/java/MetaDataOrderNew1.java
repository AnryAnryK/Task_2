import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class MetaDataOrderNew1 {

	private static final String ORDER_JSON1 = "{\"ingredients\": [\"61c0c5a71d1f82001bdaaa6d\", \"61c0c5a71d1f82001bdaaa6f\"]}";

	private static final String ORDER_INCORRECT_JSON1 = "{\"ingredients\": [\"_61c0c5a71d1f82001bdaaa6d\", \"_61c0c5a71d1f82001bdaaa6f\"]}";

	String email;
	String password;
	String name;

	String accessToken;

	public MetaDataOrderNew1() {
	}


	@BeforeEach
	public void setUp() {
		RestAssured.baseURI = ("https://stellarburgers.education-services.ru/");
	}


	@Step("получить список ингредиентов")

	public void getIngredients() {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.when()
				.get("api/ingredients")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("success", equalTo(true))
				.assertThat().body("data._id", everyItem(notNullValue()));
	}

	@Step("создать заказ с ингредиентами")

	public void createOrderFromIngredients() {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(ORDER_JSON1)
				.when()
				.post("api/orders")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("success", equalTo(true))
				.assertThat().body("order.number", notNullValue());
	}

	@Step("попробовать создать заказ Без ингредиентов")

	public void createOrderWithoutIngredients() {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body("")
				.when()
				.post("api/orders")
				.then()
				.statusCode(400)
				.log().all()
				.assertThat().body("success", equalTo(false))
				.assertThat().body("message", is("Ingredient ids must be provided"));
	}

	@Step("попробовать создать заказ с некорректными хеш ингредиентов")

	public void createOrderWithIncorrectHashIngredients() {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(ORDER_INCORRECT_JSON1)
				.when()
				.post("api/orders")
				.then()
				.statusCode(500)
				.log().all();
	}

	@Step("выход из авторизации")

	public void logOutAfterAuthorization() {
		MetaDataUserNew1 metaDataUserNew1 = new MetaDataUserNew1("email1115@mail.com", "email1115@mail", "email1115");

		Response response = given()
				.header("Content-type", "application/json")
				.log().all()
				.body("{\"email\":\"" + metaDataUserNew1.email + "\", \"password\":\"" + metaDataUserNew1.password + "\"}")
				.when()
				.post("api/auth/login")
				.then()
				.statusCode(200)
				.log().all()
				.body("success", equalTo(true))
				.extract()
				.response();

		String refreshToken = response.path("refreshToken");

		given()
				.header("Content-type", "application/json")
				.log().all()
				.body("{\"token\": \"" + refreshToken + "\"}")  // refreshToken!
				.when()
				.post("api/auth/logout")
				.then()
				.statusCode(200)
				.log().all()
				.body("success", equalTo(true))
				.body("message", equalTo("Successful logout"));
	}

	@Step("без выхода из авторизации")

	public void withoutLogOutAfterAuthorization() {
		MetaDataUserNew1 metaDataUserNew1 = new MetaDataUserNew1("email1115@mail.com", "email1115@mail", "email1115");

		given()
				.header("Content-type", "application/json")
				.log().all()
				.body("{\"email\":\"" + metaDataUserNew1.email + "\", \"password\":\"" + metaDataUserNew1.password + "\"}")
				.when()
				.post("api/auth/login")
				.then()
				.statusCode(200)
				.log().all()
				.body("success", equalTo(true))
				.extract()
				.response();
	}
}
