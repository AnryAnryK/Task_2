import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

public class ChangeUserDataTestNew1 extends ChangeMetaDataUserNew1 {

	@Test
	@Description("Изменить данные под Авторизированным пользователем")
	public void changeDataUnderAutorizationUserPositiveTest() {
		ChangeMetaDataUserNew1 changeMetaDataUserNew1 = ChangeMetaDataUserNew1.changeUser();  // //в этом коде всё работает, но не забывайте про тестовые данные, которые часто "протухают", поэтому иногда нужно  в changeUser() указать НОВЫЕ данные
		changeMetaDataUserNew1.changeUserDataPositiveTest(changeMetaDataUserNew1);
	}

	@Test
	@Description("Попробовать Изменить данные под НЕавторизированным пользователем")
	public void changeDataWithoutAutorizationUserPositiveTest() {
		ChangeMetaDataUserNew1 changeMetaDataUserNew1 = ChangeMetaDataUserNew1.changeUser();
		changeMetaDataUserNew1.changeUserDataWithoutAuthorizationPositiveTest(changeMetaDataUserNew1);
	}
}





