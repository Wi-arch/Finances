package by.training.finance.validator;

public class UserValidator {

	private final static String LOGIN_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{5,}$";
	private final static String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\s\\w])[\\S]{5,}$";

	public static boolean checkLogin(String login) {

		if (login == null || !login.matches(LOGIN_REGEX)) {
			return false;
		}
		return true;
	}

	public static boolean checkPassword(String password) {

		if (password == null || !password.matches(PASSWORD_REGEX)) {
			return false;
		}
		return true;
	}
}
