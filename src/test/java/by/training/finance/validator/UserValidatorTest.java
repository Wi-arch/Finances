package by.training.finance.validator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserValidatorTest {

	@Test
	public void testUserLoginNegative() {
		String login = "QWErty";
		boolean actual = UserValidator.checkLogin(login);
		assertEquals(actual, false);
	}

	@Test
	public void testUserLoginPositive() {
		String login = "Q1wrTY5";
		boolean actual = UserValidator.checkLogin(login);
		assertEquals(actual, true);
	}

	@Test
	public void testUserPasswordNegative() {
		String login = "acb123";
		boolean actual = UserValidator.checkPassword(login);
		assertEquals(actual, false);
	}

	@Test
	public void testUserPasswordPositive() {
		String login = "Q...777wrTY";
		boolean actual = UserValidator.checkPassword(login);
		assertEquals(actual, true);
	}
}
