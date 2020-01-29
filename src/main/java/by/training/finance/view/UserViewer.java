package by.training.finance.view;

import static by.training.finance.propertiesManager.MessageManager.getMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import by.training.finance.controller.Controller;
import by.training.finance.controller.command.CommandNameManager;

public class UserViewer {

	private final static Controller CONTROLLER = Controller.getInstance();
	private final static Scanner SCANNER = new Scanner(System.in);
	private final static String SUCCESSFUL_RESPONSE = "<Status200>";
	private static String sessionLogin;

	public static void showMainMenu() {

		while (true) {

			System.out.println(getMessage("menu.mainMenu"));
			String choice = SCANNER.nextLine();

			switch (choice) {
			case "1":
				CardViewer.showCardOperations();
				break;
			case "2":
				MessageViewer.showMessagesOperations();
				break;
			case "3":
				if (showUserOperations()) {
					return;
				}
				break;
			case "4":
				if (logOut()) {
					return;
				}
				break;
			default:
				System.out.println(getMessage("message.wrongCommand"));
			}
		}
	}

	public static boolean showUserOperations() {

		while (true) {
			System.out.println(getMessage("menu.userOperations"));
			String choice = SCANNER.nextLine();

			switch (choice) {
			case "1":
				changePassword();
				break;
			case "2":
				if (deleteUser()) {
					return true;
				}
				break;
			case "3":
				return false;
			default:
				System.out.println(getMessage("message.wrongCommand"));
			}
		}
	}

	public static boolean deleteUser() {

		boolean flag = false;
		System.out.println(getMessage("message.enterPassword"));
		String password = SCANNER.nextLine();

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.DELETE_USER);
		request.put("login", sessionLogin);
		request.put("password", password);

		System.out.println(getMessage("message.accountDeletionConfirmation"));
		String ConfirmAnswer = SCANNER.nextLine();
		if (!ConfirmAnswer.equalsIgnoreCase("Yes")) {
			return flag;
		}

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("user.delete.ok"));
			sessionLogin = null;
			flag = true;
		} else {
			System.out.println(getMessage(answer));
		}
		return flag;
	}

	public static void changePassword() {

		System.out.println(getMessage("message.enterNewPassword"));
		String password = SCANNER.nextLine();
		System.out.println(getMessage("message.enterConfirmPassword"));
		String passwordConfirm = SCANNER.nextLine();

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.UPDATE_PASSWORD);
		request.put("login", sessionLogin);
		request.put("password", password);
		request.put("passwordConfirm", passwordConfirm);

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("user.password.changed.ok"));
		} else {
			System.out.println(getMessage(answer));
		}
	}

	public static void signIn() {

		System.out.println(getMessage("message.enterLogin"));
		String login = SCANNER.nextLine();
		System.out.println(getMessage("message.enterPassword"));
		String password = SCANNER.nextLine();

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.SIGN_IN);
		request.put("login", login);
		request.put("password", password);

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("user.signin.ok") + " " + login);
			sessionLogin = login;
			showMainMenu();
		} else {
			System.out.println(getMessage(answer));
		}
	}

	public static boolean logOut() {

		boolean flag = false;

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.LOG_OUT);
		request.put("login", sessionLogin);

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("user.logout.ok"));
			sessionLogin = null;
			flag = true;
		} else {
			System.out.println(getMessage(answer));
		}
		return flag;
	}

	static String getSessionLogin() {
		return sessionLogin;
	}

}
