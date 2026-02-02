package New.helpers;

import New.User;
import New.UserApi;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class GetOrdersHelperForGetOrders {

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
}
