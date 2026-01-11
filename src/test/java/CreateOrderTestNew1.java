import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;


public class CreateOrderTestNew1 extends MetaDataOrderNew1 {   //в этом коде всё работает, но не забывайте про тестовые данные, которые часто "протухают" и то, что под каким-то юзером сначала нужно ВЫЙТИ, чтобы проверить логирование, а под каким-то, наоборот, сначала ЗАЙТИ (какая у вас будет ситуация во время ревью - неизвестно, поэтому нужно соблюдать последовательность логики вашего сваггера)

	@Test
	@Description("проверить наличие / доступность всех ингредиентов")
	public void checkIngredients() {
		MetaDataOrderNew1 metaDataOrderNew1 = new MetaDataOrderNew1();
		metaDataOrderNew1.getIngredients();
	}

	@Test
	@Description("создать заказ из ингредиентов")
	public void createOrderFromIngredients() {
		MetaDataOrderNew1 metaDataOrderNew1 = new MetaDataOrderNew1();
		metaDataOrderNew1.createOrderFromIngredients();
	}

	@Test
	@Description("попробовать создать заказ Без ингредиентов")
	public void createOrderWithoutIngredients() {
		MetaDataOrderNew1 metaDataOrderNew1 = new MetaDataOrderNew1();
		metaDataOrderNew1.createOrderWithoutIngredients();
	}

	@Test
	@Description("попробовать создать заказ с некорректными хеш ингредиентов")
	public void createOrderWithIncorrectHashIngredients() {
		MetaDataOrderNew1 metaDataOrderNew1 = new MetaDataOrderNew1();
		metaDataOrderNew1.createOrderWithIncorrectHashIngredients();
	}

	@Test
	@Description("попробовать создать заказ БЕЗ авторизации")
	public void createOrderWithoutAuthorization() {
		MetaDataOrderNew1 metaDataOrderNew1 = new MetaDataOrderNew1();
		metaDataOrderNew1.logOutAfterAuthorization();

		MetaDataOrderNew1 metaDataOrderNew11 = new MetaDataOrderNew1();
		metaDataOrderNew11.createOrderFromIngredients();
	}

	@Test
	@Description("попробовать создать заказ c авторизацией")
	public void createOrderWithAuthorization() {
		MetaDataOrderNew1 metaDataOrderNew1 = new MetaDataOrderNew1();
		metaDataOrderNew1.withoutLogOutAfterAuthorization();

		MetaDataOrderNew1 metaDataOrderNew11 = new MetaDataOrderNew1();
		metaDataOrderNew11.createOrderFromIngredients();
	}
}
