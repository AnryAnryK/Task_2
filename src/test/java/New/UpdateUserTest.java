package New;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

public class UpdateUserTest extends BaseTest {

	@Test
	@Description("Изменить данные под Авторизированным пользователем")
	public void changeDataUnderAutorizationUserPositiveTest() {
		UserApi.changeUserDataPositiveTest(userDataGenerator.asUserDataGenerator());
	}

	@Test
	@Description("Попробовать Изменить данные под НЕавторизированным пользователем")
	public void changeDataWithoutAutorizationUserPositiveTest() {
		UserApi.changeUserDataWithoutAuthorizationPositiveTest(userDataGenerator.asUserDataGenerator());
	}
}
