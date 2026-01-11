import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;


public class GetOrderTestNew1 extends MetaDataGetOrderNew1 {  //в этом коде всё работает, но не забывайте про тестовые данные, которые часто "протухают" и то, что под каким-то юзером сначала нужно ВЫЙТИ, чтобы проверить логирование, а под каким-то, наоборот, сначала ЗАЙТИ (какая у вас будет ситуация во время ревью - неизвестно, поэтому нужно соблюдать последовательность логики вашего сваггера)

	@Test
	@Description("получить список заказов конкретного пользователя БЕЗ авторизации")
	public void getOrderWithoutAuthorization() {
		MetaDataGetOrderNew1 metaDataGetOrderNew1 = new MetaDataGetOrderNew1();
		metaDataGetOrderNew1.getOrderWithoutAuthorization();
	}

	@Test
	@Description("получить список заказов конкретного пользователя c авторизацией")
	public void getOrderWithAuthorization() {
		MetaDataGetOrderNew1 metaDataGetOrderNew1 = new MetaDataGetOrderNew1();
		metaDataGetOrderNew1.getOrderWithAuthorization();
	}
}
