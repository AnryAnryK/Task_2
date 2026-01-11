import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTestNew1 extends MetaDataUserNew1 {

	@Test
	@Description("Создать нового уникального Пользователя")
	public void createNewUniqueUserPositiveTest() {
		MetaDataUserNew1 metaDataUserNew1 = MetaDataUserNew1.defaultUser1(); // именно этот код работает, но, т.к. данные по юзерам регулярно могут "протухать" то, если это произойдёт (а это может произойти когда угодно с момента сдачи мной работы и вашего ревью) - не забывайте менять данные в defaultUser1()
		accessToken = createNewUniqeUserPositiveTest(metaDataUserNew1);
	}

	@Test
	@Description("Создать уже существующего Пользователя")
	public void createAlreadyExistingUserTest() {
		MetaDataUserNew1 metaDataUserNew1 = MetaDataUserNew1.defaultUser1AlredyExist();
		createNewUniqeUserNegaiveTest(metaDataUserNew1);
	}


	@Test
	@Description("Залогиниться (авторизоваться) под Пользователем")
	public void autorizationUserTest() {
		MetaDataUserNew1 metaDataUserNew1 = MetaDataUserNew1.defaultUser1AlredyExist();
		metaDataUserNew1.loginAuthorizationUserPositiveTest(metaDataUserNew1);
	}

	@ParameterizedTest
	@MethodSource("credentialsProvider")
	@Description("Создать уже существующего Пользователя")
	@Step("Параметризовано проверяем, что при незаполнении одного из полей будет получена 403 ошибка с соответствующим текстом ошибки")
	public void createUserWithoutOneOfRequiredFieldTest() {

		MetaDataUserNew1 metaDataUserNew1 = new MetaDataUserNew1(email, password, name);

		given()
				.header("Content-type", "application/json")
				.log().all()
				.body(metaDataUserNew1)
				.when()
				.post("api/auth/register")
				.then()
				.statusCode(403)
				.log().all()
				.assertThat().body("success", equalTo(false))
				.assertThat().body("message", equalTo("Email, password and name are required fields"));
	}

	private static Stream<Arguments> credentialsProvider() {
		return Stream.of(
				Arguments.of("email155@mail.com", "email155@mail"),
				Arguments.of("email155@mail.com", "email155"),
				Arguments.of("email155@mail", "email155")
		);
	}
}





