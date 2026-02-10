package New;

import com.github.javafaker.Faker;

public class UserDataGenerator {

	String email;
	String password;
	String name;

	static Faker faker = new Faker();

	public UserDataGenerator(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}

	public UserDataGenerator() {
		this.email = faker.internet().emailAddress();
		this.password = faker.internet().password();
		this.name = faker.name().fullName();
	}

	public User asUserDataGenerator() {
		return new User(email, password, name);
	}

	public static UserDataGenerator defaultUser1() {
		return new UserDataGenerator("email11261@mail.com", "email11261@mail", "email11261");
	}
}