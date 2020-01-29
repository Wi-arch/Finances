package by.training.finance.view;

import static by.training.finance.propertiesManager.MessageManager.getMessage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import by.training.finance.bean.Message;
import by.training.finance.controller.Controller;
import by.training.finance.controller.command.CommandNameManager;

public class MessageViewer {

	private final static Controller CONTROLLER = Controller.getInstance();
	private final static Scanner SCANNER = new Scanner(System.in);
	private final static String SUCCESSFUL_RESPONSE = "<Status200>";

	public static void showMessagesOperations() {

		while (true) {
			System.out.println(getMessage("menu.messageOperations"));
			String choice = SCANNER.nextLine();

			switch (choice) {
			case "1":
				sendMessage();
				break;
			case "2":
				showAllInputMessages();
				break;
			case "3":
				showAllOutputMessages();
				break;
			case "4":
				return;
			default:
				System.out.println(getMessage("message.wrongCommand"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void showAllInputMessages() {

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.GET_ALL_INPUT_MESSAGES);
		request.put("login", UserViewer.getSessionLogin());

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {

			List<Message> messages = (List<Message>) response.get("messages");
			if (!messages.isEmpty()) {
				iterateOverMessages(messages, false);
			} else {
				System.out.println(getMessage("message.noIncomingMessages"));
			}
		} else {
			System.out.println(getMessage(answer));
		}
	}

	@SuppressWarnings("unchecked")
	private static void showAllOutputMessages() {

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.GET_ALL_OUTPUT_MESSAGES);
		request.put("login", UserViewer.getSessionLogin());

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {

			List<Message> messages = (List<Message>) response.get("messages");
			if (!messages.isEmpty()) {
				iterateOverMessages(messages, true);
			} else {
				System.out.println(getMessage("message.noOutputMessages"));
			}
		} else {
			System.out.println(getMessage(answer));
		}
	}

	private static void sendMessage() {

		System.out.println(getMessage("message.enterReceiverLogin"));
		String receiverLogin = SCANNER.nextLine();
		System.out.println(getMessage("message.enterMessageText"));
		String body = SCANNER.nextLine();

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.SEND_MESSAGE);
		request.put("senderLogin", UserViewer.getSessionLogin());
		request.put("receiverLogin", receiverLogin);
		request.put("body", body);

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("message.send.ok"));
		} else {
			System.out.println(getMessage(answer));
		}
	}

	private static void iterateOverMessages(List<Message> messages, boolean outputMessages) {

		int i = 0;
		printMessage(messages.get(i), outputMessages);
		while (true) {
			System.out.println(getMessage("menu.iterateOverMessages"));
			String choice = SCANNER.nextLine();
			switch (choice) {
			case "1":
				i = i < messages.size() - 1 ? ++i : 0;
				printMessage(messages.get(i), outputMessages);
				break;
			case "2":
				i = i > 0 ? --i : messages.size() - 1;
				printMessage(messages.get(i), outputMessages);
				break;
			case "0":
				return;
			default:
				System.out.println(getMessage("message.wrongCommand"));
			}
		}
	}

	private static void printMessage(Message message, boolean outputMessages) {

		if (outputMessages) {
			System.out.println(getMessage("message.receiver") + message.getReceiver());
		} else {
			System.out.println(getMessage("message.sender") + message.getReceiver());
		}
		System.out.println(getMessage("message.creationDate") + message.getCreationDate());
		System.out.println(getMessage("message.messageText") + message.getBody());
	}

}
