import io.qameta.allure.Step;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ChangeMetaDataUserNew1 {
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

	public ChangeMetaDataUserNew1(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}

	public static ChangeMetaDataUserNew1 changeUser() {
		return new ChangeMetaDataUserNew1("email2202@mail.com", "email2202@mail", "email2202");  // не забывайте менять эти данные, чтобы тесты НЕ падали !
	}

	@BeforeEach
	public void setUp() {
		RestAssured.baseURI = ("https://stellarburgers.education-services.ru/");
	}


	public ChangeMetaDataUserNew1() {
	}

	@Step("смена данных юзера после логина в систему с авторизацией")
	public void changeUserDataPositiveTest(ChangeMetaDataUserNew1 changeMetaDataUserNew) {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(changeMetaDataUserNew)
				.when()
				.post("api/auth/register")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("user.email", equalTo(changeMetaDataUserNew.email))
				.assertThat().body("user.name", equalTo(changeMetaDataUserNew.name))
				.extract()
				.path("accessToken");


		accessToken =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body(changeMetaDataUserNew)
						.when()
						.post("api/auth/login")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("success", equalTo(true))
						.assertThat().body("user.email", equalTo(changeMetaDataUserNew.email))
						.assertThat().body("user.name", equalTo(changeMetaDataUserNew.name))
						.extract()
						.path("accessToken");

		given()
				.header("Content-type", "application/json")
				.header("Authorization", accessToken)
				.log().all()
				.body(changeMetaDataUserNew)
				.when()
				.get("api/auth/user")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("success", equalTo(true))
				.assertThat().body("user.email", equalTo(changeMetaDataUserNew.email))
				.assertThat().body("user.name", equalTo(changeMetaDataUserNew.name))
				.extract()
				.path("accessToken");


		String changeEmail = "change_" + changeMetaDataUserNew.email;
		String changePassword = "change_" + changeMetaDataUserNew.password;
		String changeName = "change_" + changeMetaDataUserNew.name;

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
	public void changeUserDataWithoutAuthorizationPositiveTest(ChangeMetaDataUserNew1 changeMetaDataUserNew) {
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(changeMetaDataUserNew)
				.when()
				.post("api/auth/register")
				.then()
				.statusCode(200)
				.log().all()
				.assertThat().body("user.email", equalTo(changeMetaDataUserNew.email))
				.assertThat().body("user.name", equalTo(changeMetaDataUserNew.name))
				.extract()
				.path("accessToken");


		accessToken =
				given()
						.header("Content-type", "application/json")
						.log().all()
						.body(changeMetaDataUserNew)
						.when()
						.post("api/auth/login")
						.then()
						.statusCode(200)
						.log().all()
						.assertThat().body("success", equalTo(true))
						.assertThat().body("user.email", equalTo(changeMetaDataUserNew.email))
						.assertThat().body("user.name", equalTo(changeMetaDataUserNew.name))
						.extract()
						.path("accessToken");

		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(changeMetaDataUserNew)
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

}