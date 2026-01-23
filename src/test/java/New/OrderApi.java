package New;

import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class OrderApi extends OrderBase {

	@Step("получить список ингредиентов")
	public static void getIngredients() {
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
	public static void createOrderFromIngredients() {
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
	public static void createOrderWithoutIngredients() {
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
	public static void createOrderWithIncorrectHashIngredients() {
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

	@Step("получить список заказов конкретного пользователя БЕЗ авторизации")
	public static void getOrderWithoutAuthorization() {
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

	public static void getOrderWithAuthorization(User user) {
		String accessToken = UserApi.simpleLogin(user);

		given()
				.header("Content-type", "application/json")
				.header("Authorization", accessToken)
				.log().all()
				.when()
				.get("api/orders")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("success", equalTo(true));
	}
}
