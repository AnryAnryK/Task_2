package New;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CreateUserTest extends BaseTest {

	String accessToken;

	private static Stream<Arguments> credentialsProvider() {
		return Stream.of(
				Arguments.of("email155@mail.com", "email155@mail", null),
				Arguments.of("email155@mail.com", null, "email155"),
				Arguments.of(null, "email155@mail", "email155")
		);
	}

	@Test
	@Description("Создать нового уникального Пользователя")
	public void createNewUniqueUserPositiveTest() {
		accessToken = UserApi.createNewUniqeUserPositiveTest(userDataGenerator.asUserDataGenerator());
		assertNotNull(accessToken);
	}

	@Test
	@Description("попытка повторного создания одинакового уникального юзера")
	public void createNewUniqeUserNegaiveTest() {
		accessToken = UserApi.createNewUniqeUserPositiveTest(userDataGenerator.asUserDataGenerator());
		assertNotNull(accessToken);
		UserApi.createNewUniqeUserNegaiveTest(userDataGenerator.asUserDataGenerator());
	}

	@ParameterizedTest
	@MethodSource("credentialsProvider")
	@Description("Создать уже существующего Пользователя")
	@Step("Параметризовано проверяем, что при незаполнении одного из полей будет получена 403 ошибка с соответствующим текстом ошибки")
	public void createUserWithoutOneOfRequiredFieldTest(String email, String password, String name) {
		User user1 = new User(email, password, name);
		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(user1)
				.when()
				.post("api/auth/register")
				.then()
				.statusCode(403)
				.log().all()
				.assertThat().body("success", equalTo(false))
				.assertThat().body("message", equalTo("Email, password and name are required fields"));
	}
}
