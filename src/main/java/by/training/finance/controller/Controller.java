package by.training.finance.controller;

import java.util.Map;

import by.training.finance.command.Command;
import by.training.finance.command.CommandNameManager;
import by.training.finance.command.impl.WrongRequest;

public class Controller {

	private final static Controller instance = new Controller();

	private Controller() {
	}

	public Map<String, Object> execute(Map<String, Object> request) {

		Command command = null;

		if (request == null || !(request.get("command") instanceof CommandNameManager)) {
			command = new WrongRequest();
		} else {
			CommandNameManager commandName = (CommandNameManager) request.get("command");
			command = commandName.getCommand();
		}

		Map<String, Object> response = command.execute(request);
		return response;
	}

	public static Controller getInstance() {
		return instance;
	}

}
