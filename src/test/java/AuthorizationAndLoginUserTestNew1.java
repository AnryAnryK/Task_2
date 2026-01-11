import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;

public class AuthorizationAndLoginUserTestNew1 extends MetaDataUserNew1 {   //в этом коде всё работает, но не забывайте про тестовые данные, которые часто "протухают" и то, что под каким-то юзером сначала нужно ВЫЙТИ, чтобы проверить логирование, а под каким-то, наоборот, сначала ЗАЙТИ (какая у вас будет ситуация во время ревью - неизвестно, поэтому нужно соблюдать последовательность логики вашего сваггера)

	@Test
	@Description("Создать нового уникального Пользователя")
	public void createNewUniqeUserPositiveTest() {
		MetaDataUserNew1 metaDataUserNew1 = MetaDataUserNew1.defaultUser2();  // если хотите запускать ВСЕ тесты данного Класса сразу - не забудьте поменять данные defaultUser1()  (иначе тесты будут могут, говоря, что "уже есть похожий созданный юзер", хотя у меня и есть метод cleanUpUser(), но если запускать не все тесты сразу, а только один тест на Создание, то юзер удаляться не будет)
		accessToken = createNewUniqeUserPositiveTest(metaDataUserNew1);
	}

	@Test
	@Description("Залогиниться (авторизоваться) под АВТОРИЗОВАННЫМ Пользователем")
	public void autorizationUserPositiveTest() {
		MetaDataUserNew1 metaDataUserNew1 = MetaDataUserNew1.defaultUser1AlredyExist(); // если хотите запускать ВСЕ тесты данного Класса сразу - не забудьте поменять данные defaultUser1AlredyExist()  уже ранее созданного пользователя (данные можно взять, например, у defaultUser1()), предварительно запустив тест по Созданию юзера !!
		metaDataUserNew1.loginAuthorizationUserPositiveTest(metaDataUserNew1);
	}

	@Test
	@Description("Попытка войти под Пользователем БЕЗ авторизации")
	public void withoutAutorizationUserPositiveTest() {
		MetaDataUserNew1 metaDataUserNew1 = MetaDataUserNew1.defaultUser3(); // не забывайте менять эти данные и, либо, выходить из системы, либо подставлять данные другого, не залогининого юзера, например defaultUser3(), чтобы тесты НЕ падали !
		metaDataUserNew1.loginWithoutAuthorizationUserPositiveTest2(metaDataUserNew1);
	}

	@Test
	@Description("Залогиниться (авторизоваться) под Пользователем с неверным логином и паролем")
	public void autorizationUserNegativeTest() {
		MetaDataUserNew1 metaDataUserNew1 = MetaDataUserNew1.incorrectDefaultUser1();
		metaDataUserNew1.loginUserIncorrectMailPasswordTest(metaDataUserNew1);
	}

	@Test
	@Description("Изменить данные под Авторизированным пользователем")
	public void changeDataUnderAutorizationUserPositiveTest() {
		MetaDataUserNew1 metaDataUserNew1 = MetaDataUserNew1.changeUser(); // не забывайте менять эти данные changeUser(), чтобы тесты НЕ падали !
		metaDataUserNew1.changeUserDataPositiveTest(metaDataUserNew1);
	}

	@Test
	@Description("Попробовать Изменить данные под НЕавторизированным пользователем")
	public void changeDataWithoutAutorizationUserPositiveTest() {
		MetaDataUserNew1 metaDataUserNew1 = MetaDataUserNew1.changeUser();  // не забывайте менять эти данные changeUser(), чтобы тесты НЕ падали !
		metaDataUserNew1.changeUserDataWithoutAuthorizationPositiveTest(metaDataUserNew1);
	}
}





