package New;

public class User {

	public String email;
	public String password;
	public String name;

	String accessToken;

	public User(String email, String password, String name) {
		this.email = email;
		this.password = password;
		this.name = name;
	}

	public User(String email, String password) {
		this.email = email;
		this.password = password;

	}

	public User() {
	}

	public User(UserDataGenerator userDataGenerator) {
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getEmail(String email) {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword(String password) {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName(String name) {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}