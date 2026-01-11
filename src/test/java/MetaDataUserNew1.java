import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class MetaDataUserNew1 {

	String email;
	String password;
	String name;

	String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public static MetaDataUserNew1 defaultUser1() {
		return new MetaDataUserNew1("email11261@mail.com", "email11261@mail", "email11261");  // не забывайте менять эти данные чтобы тесты НЕ падали !
	}

	public static MetaDataUserNew1 defaultUser2() {
		return new MetaDataUserNew1("email21261@mail.com", "email21261@mail", "email21261");  // не забывайте менять эти данные чтобы тесты НЕ падали !
	}

	public static MetaDataUserNew1 defaultUser1AlredyExist() {
		return new MetaDataUserNew1("email1109@mail.com", "email1109@mail", "email1109");
	}

	public static MetaDataUserNew1 defaultUser1AlredyExist2() {
		return new MetaDataUserNew1("email11011@mail.com", "email11011@mail", "email11011");
	}

	public static MetaDataUserNew1 defaultUser3() {
		return new MetaDataUserNew1("email3306@mail.com", "email3306@mail", "email3306");  // не забывайте менять эти данные чтобы тесты НЕ падали !
	}

	public static MetaDataUserNew1 incorrectDefaultUser1() {
		return new MetaDataUserNew1("0email166@mail.com", "0email166@mail", "0email166");
	}

	public static MetaDataUserNew1 changeUser() {
		return new MetaDataUserNew1("email4404@mail.com", "email4404@mail", "email4404");  // не забывайте менять эти данные changeUser(), чтобы тесты НЕ падали !
	}

	@BeforeEach
	public void setUp() {
		RestAssured.baseURI = ("https://stellarburgers.education-services.ru/");
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MetaDataUserNew1(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}

	public MetaDataUserNew1(String email, String password) {
		this.email = email;
		this.password = password;

	}

	public MetaDataUserNew1() {
	}

	@Step("создание уникального юзера")
	public String createNewUniqeUserPositiveTest(MetaDataUserNew1 metaDataUserNew1) {
		return
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body("{\"email\":\"" + metaDataUserNew1.email + "\", \"password\":\"" + metaDataUserNew1.password + "\", \"name\":\"" + metaDataUserNew1.name + "\"}")
						.when()
						.post("api/auth/register")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
						.assertThat().body("user.name", equalTo(metaDataUserNew1.name))
						.extract()
						.path("accessToken");
	}

	@Step("попытка повторного создания точно такого же уникального юзера")
	public void createNewUniqeUserNegaiveTest(MetaDataUserNew1 metaDataUserNew1) {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body("{\"email\":\"" + metaDataUserNew1.email + "\", \"password\":\"" + metaDataUserNew1.password + "\", \"name\":\"" + metaDataUserNew1.name + "\"}")
				.when()
				.post("api/auth/register")
				.then()
				.statusCode(403)
				.log().all()
				.assertThat().body("success", equalTo(false))
				.assertThat().body("message", equalTo("User already exists"));
	}

	@Step("логин в систему через авторизацию")
	public void loginAuthorizationUserPositiveTest(MetaDataUserNew1 metaDataUserNew1) {
		accessToken =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body("{\"email\":\"" + metaDataUserNew1.email + "\", \"password\":\"" + metaDataUserNew1.password + "\"}")
						.when()
						.post("api/auth/login")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("success", equalTo(true))
						.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
						.assertThat().body("user.name", equalTo(metaDataUserNew1.name))
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
				.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
				.assertThat().body("user.name", equalTo(metaDataUserNew1.name));
	}

	@Step("логин в систему через авторизацию")
	public void loginAuthorizationUserWithoutDeleteDataPositiveTest(MetaDataUserNew1 metaDataUserNew1) {
		accessToken =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body(metaDataUserNew1)
						.when()
						.post("api/auth/login")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("success", equalTo(true))
						.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
						.assertThat().body("user.name", equalTo(metaDataUserNew1.name))
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
				.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
				.assertThat().body("user.name", equalTo(metaDataUserNew1.name));

	}

	@Step("только логин без проверок и удаления")
	public String simpleLogin(MetaDataUserNew1 metaDataUserNew1) {
		return
				given()
				.header("Content-type", "application/json")
				.log().all()
				.body("{\"email\":\"" + metaDataUserNew1.email + "\", \"password\":\"" + metaDataUserNew1.password + "\"}")
				.when()
				.post("api/auth/login")
				.then()
				.statusCode(200)
				.extract()
				.path("accessToken");

	}

	@Step("попытка логина в систему без авторизации")
	public void loginWithoutAuthorizationUserPositiveTest(MetaDataUserNew1 metaDataUserNew1) {
		Response response =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body(metaDataUserNew1)
						.when()
						.post("api/auth/login")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("success", equalTo(true))
						.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
						.assertThat().body("user.name", equalTo(metaDataUserNew1.name))
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

	@Step("попытка логина в систему без авторизации")
	public void loginWithoutAuthorizationUserPositiveTest2(MetaDataUserNew1 metaDataUserNew1) {

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

	@Step("попытка логина в систему с неверными данными")
	public void loginUserIncorrectMailPasswordTest(MetaDataUserNew1 metaDataUserNew1) {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(metaDataUserNew1)
				.when()
				.post("api/auth/login")
				.then()
				.statusCode(401)
				.log().all()
				.assertThat().body("success", not(true))
				.assertThat().body("message", is("email or password are incorrect"));
	}

	@Step("смена данных юзера после логина в систему с авторизацией")
	public void changeUserDataPositiveTest(MetaDataUserNew1 metaDataUserNew1) {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(metaDataUserNew1)
				.when()
				.post("api/auth/register")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
				.assertThat().body("user.name", equalTo(metaDataUserNew1.name))
				.extract()
				.path("accessToken");


		accessToken =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body(metaDataUserNew1)
						.when()
						.post("api/auth/login")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("success", equalTo(true))
						.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
						.assertThat().body("user.name", equalTo(metaDataUserNew1.name))
						.extract()
						.path("accessToken");

		given()
				.header("Content-type", "application/json")
				.header("Authorization", accessToken)
				.log().all()
				.body(metaDataUserNew1)
				.when()
				.get("api/auth/user")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("success", equalTo(true))
				.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
				.assertThat().body("user.name", equalTo(metaDataUserNew1.name))
				.extract()
				.path("accessToken");


		String changeEmail = "change_" + metaDataUserNew1.email;
		String changePassword = "change_" + metaDataUserNew1.password;
		String changeName = "change_" + metaDataUserNew1.name;

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
	public void changeUserDataWithoutAuthorizationPositiveTest(MetaDataUserNew1 metaDataUserNew1) {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(metaDataUserNew1)
				.when()
				.post("api/auth/register")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
				.assertThat().body("user.name", equalTo(metaDataUserNew1.name))
				.extract()
				.path("accessToken");


		accessToken =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body(metaDataUserNew1)
						.when()
						.post("api/auth/login")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("success", equalTo(true))
						.assertThat().body("user.email", equalTo(metaDataUserNew1.email))
						.assertThat().body("user.name", equalTo(metaDataUserNew1.name))
						.extract()
						.path("accessToken");

		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(metaDataUserNew1)
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