package New;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

public class CreateOrderTest extends BaseTest {

	@Test
	@Description("проверить наличие / доступность всех ингредиентов")
	public void checkIngredients() {
		OrderApi.getIngredients();
	}

	@Test
	@Description("создать заказ из ингредиентов")
	public void createOrderFromIngredients() {
		OrderApi.createOrderFromIngredients();
	}

	@Test
	@Description("попробовать создать заказ Без ингредиентов")
	public void createOrderWithoutIngredients() {
		OrderApi.createOrderWithoutIngredients();
	}

	@Test
	@Description("попробовать создать заказ с некорректными хеш ингредиентов")
	public void createOrderWithIncorrectHashIngredients() {
		OrderApi.createOrderWithIncorrectHashIngredients();
	}

	@Test
	@Description("попробовать создать заказ БЕЗ авторизации")
	public void createOrderWithoutAuthorization() {
		accessToken = UserApi.createNewUniqeUserPositiveTest(userDataGenerator.asUserDataGenerator());
		UserApi.loginWithoutAuthorizationUserPositiveTest(userDataGenerator.asUserDataGenerator());
		OrderApi.createOrderFromIngredients();
	}

	@Test
	@Description("попробовать создать заказ c авторизацией")
	public void createOrderWithAuthorization() {
		accessToken = UserApi.createNewUniqeUserPositiveTest(userDataGenerator.asUserDataGenerator());
		UserApi.loginAuthorizationUserPositiveTest(userDataGenerator.asUserDataGenerator());
		OrderApi.createOrderFromIngredients();
	}
}
