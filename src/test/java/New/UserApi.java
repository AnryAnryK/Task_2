package New;

import New.config.RequestSpecConfig;
import io.qameta.allure.Step;
import io.restassured.response.Response;

//import static New.config.RequestSpecConfig.spec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UserApi {

	static String accessToken;

	@Step("создание уникального юзера")
	public static String createNewUniqeUserPositiveTest(User user) {
		return
//				RequestSpecConfig.getSpec()  // спека НЕ работает, как ни старался
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

	@Step("попытка повторного создания точно такого же уникального юзера")
	public static void createNewUniqeUserNegaiveTest(User user) {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(user)
				.when()
				.post("api/auth/register")
				.then()
				.statusCode(403)
				.log().all()
				.assertThat().body("success", equalTo(false))
				.assertThat().body("message", equalTo("User already exists"));
	}

	@Step("логин в систему через авторизацию")
	public static void loginAuthorizationUserPositiveTest(User user) {
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

	@Step("попытка логина в систему с неверными данными")
	public static void loginUserIncorrectMailPasswordTest(User user) {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body("{\"email\":\"" + null + "\", \"password\":\"" + null + "\"}")
				.when()
				.post("api/auth/login")
				.then()
				.statusCode(401)
				.log().all()
				.assertThat().body("success", not(true))
				.assertThat().body("message", is("email or password are incorrect"));
	}

	@Step("попытка логина в систему без авторизации")
	public static void loginWithoutAuthorizationUserPositiveTest(User user) {
		Response response =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body(user)
						.when()
						.post("api/auth/login")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("success", equalTo(true))
						.assertThat().body("user.email", equalTo(user.email))
						.assertThat().body("user.name", equalTo(user.name))
						.extract()
						.response();

		String refreshToken = response.path("refreshToken");

		given()
				.header("Content-type", "application/json")
				.log().all()
				.body("{\"token\": \"" + refreshToken + "\"}")
				.post("api/auth/logout")
				.then()
				.statusCode(200)
				.assertThat().body("success", equalTo(true))
				.assertThat().body("message", equalTo("Successful logout"));

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

	@Step("смена данных юзера после логина в систему с авторизацией")
	public static void changeUserDataPositiveTest(User user) {
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


		accessToken =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body(user)
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
				.body(user)
				.when()
				.get("api/auth/user")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("success", equalTo(true))
				.assertThat().body("user.email", equalTo(user.email))
				.assertThat().body("user.name", equalTo(user.name))
				.extract()
				.path("accessToken");


		String changeEmail = "change_" + user.email;
		String changePassword = "change_" + user.password;
		String changeName = "change_" + user.name;

		given()
				.header("Content-type", "application/json")
				.header("Authorization", accessToken)
				.log().all()
				.body("{ \"email\": \"" + changeEmail + "\", " +
						"\"password\": \"" + changePassword + "\", " +
						"\"name\": \"" + changeName + "\" }")
				.patch("api/auth/user")
				.then()
				.statusCode(200)
				.assertThat().body("success", equalTo(true));

		given()
				.header("Content-type", "application/json")
				.header("Authorization", accessToken)
				.log().all()
				.when()
				.delete("api/auth/user")
				.then()
				.statusCode(202)
				.log().all()
				.assertThat().body("success", equalTo(true))
				.assertThat().body("message", equalTo("User successfully removed"));
	}

	@Step("смена данных юзера после попытки логина в систему без авторизации")
	public static void changeUserDataWithoutAuthorizationPositiveTest(User user) {
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


		accessToken =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body(user)
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
				.log().all()
				.body(user)
				.when()
				.get("api/auth/user")
				.then()
				.statusCode(401)
				.log().all()
				.assertThat().body("success", equalTo(false))
				.assertThat().body("message", equalTo("You should be authorised"));

		given()
				.header("Content-type", "application/json")
				.header("Authorization", accessToken)
				.log().all()
				.when()
				.delete("api/auth/user")
				.then()
				.statusCode(202)
				.log().all()
				.assertThat().body("success", equalTo(true))
				.assertThat().body("message", equalTo("User successfully removed"));
	}

	@Step("только логин без проверок и удаления")
	public static String simpleLogin(User user) {
		return
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body("{\"email\":\"" + user.email + "\", \"password\":\"" + user.password + "\"}")
						.when()
						.post("api/auth/login")
						.then()
						.statusCode(200)
						.extract()
						.path("accessToken");
	}
}