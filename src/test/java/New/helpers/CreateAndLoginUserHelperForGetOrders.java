package New.helpers;

import New.User;
import io.qameta.allure.Step;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateAndLoginUserHelperForGetOrders {

	static String accessToken;

	@Step("создание уникального юзера")
	public static String createNewUniqeUserPositiveTest(User user) {
		return
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body(user)
						.when()
						.post("api/auth/register")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("user.email", equalTo(user.email))
						.assertThat().body("user.name", equalTo(user.name))
						.extract()
						.path("accessToken");
	}

	@Step("логин в систему через авторизацию")
	public static void loginAuthorizationUserHelperForGetOrders(User user) {
		accessToken =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body("{\"email\":\"" + user.email + "\", \"password\":\"" + user.password + "\"}")
						.when()
						.post("api/auth/login")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("success", equalTo(true))
						.assertThat().body("user.email", equalTo(user.email))
						.assertThat().body("user.name", equalTo(user.name))
						.extract()
						.path("accessToken");


		given()
				.header("Content-type", "application/json")
				.header("Authorization", accessToken)
				.log().all()
				.when()
				.get("api/auth/user")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("success", equalTo(true))
				.assertThat().body("user.email", equalTo(user.email))
				.assertThat().body("user.name", equalTo(user.name));
	}

	@Step("попытка логина в систему без авторизации")
	public static void loginWithoutAuthorizationUserHelperForGetOrders() {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.when()
				.get("api/auth/user")
				.then()
				.statusCode(401)
				.log().all()
				.assertThat().body("success", equalTo(false))
				.assertThat().body("message", equalTo("You should be authorised"));
	}
}
