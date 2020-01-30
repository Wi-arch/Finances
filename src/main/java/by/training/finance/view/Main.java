package by.training.finance.view;

import static by.training.finance.manager.MessageManager.getMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import by.training.finance.command.CommandNameManager;
import by.training.finance.controller.Controller;
import by.training.finance.manager.MessageManager;

public class Main {

	private final static Controller CONTROLLER = Controller.getInstance();
	private final static Scanner SCANNER = new Scanner(System.in);
	private final static String SUCCESSFUL_RESPONSE = "<Status200>";

	public static void main(String[] args) {

		while (true) {

			System.out.println(getMessage("menu.startMenu"));
			String choice = SCANNER.nextLine();
			switch (choice) {
			case "1":
				UserViewer.signIn();
				break;
			case "2":
				registration();
				break;
			case "3":
				chooseLanguage();
				break;
			case "4":
				return;
			default:
				System.out.println(getMessage("message.wrongCommand"));
			}
		}
	}

	private static void chooseLanguage() {

		while (true) {
			System.out.println(getMessage("menu.chooseLanguage"));
			String choice = SCANNER.nextLine();
			switch (choice) {
			case "1":
				MessageManager.setEnglishLocale();
				break;
			case "2":
				MessageManager.setRussianLocale();
				break;
			case "3":
				return;
			default:
				System.out.println(getMessage("message.wrongCommand"));
			}
		}
	}

	private static void registration() {

		System.out.println(getMessage("message.enterLogin"));
		String login = SCANNER.nextLine();
		System.out.println(getMessage("message.enterPassword"));
		String password = SCANNER.nextLine();
		System.out.println(getMessage("message.enterConfirmPassword"));
		String passwordConfirm = SCANNER.nextLine();

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.REGISTRATION);
		request.put("login", login);
		request.put("password", password);
		request.put("passwordConfirm", passwordConfirm);

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("user.registration.ok"));
		} else {
			System.out.println(getMessage(answer));
		}
	}

}
