package New;

import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

public class GetOrdersTest extends BaseTest {

	CreateOrderTest createOrderTest = new CreateOrderTest();

	@Test
	@Description("получить список заказов конкретного пользователя БЕЗ авторизации")
	public void getOrderWithoutAuthorization() {
		createOrderTest.createOrderWithoutAuthorization();
		OrderApi.getOrderWithoutAuthorization();
	}

	@Test
	@Description("получить список заказов конкретного пользователя c авторизацией")
	public void getOrderWithAuthorization() {
		createOrderTest.createOrderWithAuthorization();
		OrderApi.getOrderWithAuthorization(createOrderTest.userDataGenerator.asUserDataGenerator());
	}
}
