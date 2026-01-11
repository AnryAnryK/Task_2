import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class MetaDataGetOrderNew1 {



	public static MetaDataUserNew1 defaultUser3() {
		return new MetaDataUserNew1("email11011@mail.com", "email11011@mail", "email11011");
	}

	public MetaDataGetOrderNew1() {
	}

	@BeforeEach
	public void setUp() {
		RestAssured.baseURI = ("https://stellarburgers.education-services.ru/");
	}

	@Step("получить список заказов конкретного пользователя БЕЗ авторизации")

	public void getOrderWithoutAuthorization() {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.when()
				.get("api/orders")
				.then()
				.statusCode(401)
				.log().all()
				.assertThat().body("success", equalTo(false))
				.assertThat().body("message", is("You should be authorised"));
	}


	@Step("получить список заказов конкретного пользователя c авторизацией")

	public void getOrderWithAuthorization() {

		MetaDataUserNew1 metaDataUserNew1 = new MetaDataUserNew1("email1115@mail.com", "email1115@mail", "email1115");
		String token = metaDataUserNew1.simpleLogin(metaDataUserNew1);

		given()
				.header("Content-type", "application/json")
				.header("Authorization", token)
				.log().all()
				.when()
				.get("api/orders")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("success", equalTo(true));
	}
}