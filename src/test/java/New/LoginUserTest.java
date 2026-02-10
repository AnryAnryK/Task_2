package New;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class LoginUserTest extends BaseTest {

	String accessToken;

	@Test
	@Description("Залогиниться (авторизоваться) под АВТОРИЗОВАННЫМ Пользователем")
	public void autorizationUserPositiveTest() {
		accessToken = UserApi.createNewUniqeUserPositiveTest(userDataGenerator.asUserDataGenerator());
		UserApi.loginAuthorizationUserPositiveTest(userDataGenerator.asUserDataGenerator());
	}

	@ParameterizedTest
	@MethodSource("credentialsProvider")
	@Description("Залогиниться (авторизоваться) с неверным логином и паролем")
	public void autorizationUserNegativeTest() {
		accessToken = UserApi.createNewUniqeUserPositiveTest(userDataGenerator.asUserDataGenerator());
		UserApi.loginUserIncorrectMailPasswordTest(userDataGenerator.asUserDataGenerator());
	}

	private static Stream<Arguments> credentialsProvider() {
		return Stream.of(
				Arguments.of("", "", "")
		);
	}
}
