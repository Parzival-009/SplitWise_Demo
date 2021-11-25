
public class User {

	private String name;
	private int id;
	private String password;

	User(String name, Integer id, String password) {
		this.name = name;
		this.password = password;
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getId() {
		return this.id;
	}

	public boolean validatePassword(String pass) {
		return this.password.equals(pass);
	}

	public void setPassword(String oldPass, String newPass) {
		if (this.validatePassword(oldPass)) {
			this.password = newPass;
			System.out.println("Password changed succesfully");
		} else
			System.out.println("Invalid Password");
	}

}
