package New;

import New.helpers.CreateAndLoginUserHelperForGetOrders;
import New.helpers.GetOrdersHelperForGetOrders;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

public class GetOrdersTestWithHelper extends BaseTest {

	UserDataGenerator userDataGenerator = new UserDataGenerator();

	@Test
	@Description("получить список заказов конкретного пользователя c авторизацией")
	public void getOrderWithAuthorization() {
		CreateAndLoginUserHelperForGetOrders.createNewUniqeUserPositiveTest(userDataGenerator.asUserDataGenerator());
		CreateAndLoginUserHelperForGetOrders.loginAuthorizationUserHelperForGetOrders(userDataGenerator.asUserDataGenerator());
		GetOrdersHelperForGetOrders.getOrderWithAuthorization(userDataGenerator.asUserDataGenerator());
	}

	@Test
	@Description("получить список заказов конкретного пользователя БЕЗ авторизации")
	public void getOrderWithoutAuthorization() {
		CreateAndLoginUserHelperForGetOrders.createNewUniqeUserPositiveTest(userDataGenerator.asUserDataGenerator());
		CreateAndLoginUserHelperForGetOrders.loginWithoutAuthorizationUserHelperForGetOrders();
		GetOrdersHelperForGetOrders.getOrderWithoutAuthorization();
	}
}
