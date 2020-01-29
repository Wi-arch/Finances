package by.training.finance.controller.command.impl;

import java.util.HashMap;
import java.util.Map;

import by.training.finance.controller.command.Command;

public class WrongRequest implements Command {

	private final static String WRONG_COMMAND = "wrong.command";

	@Override
	public Map<String, Object> execute(Map<String, Object> request) {

		Map<String, Object> response = new HashMap<>();
		response.put("response", WRONG_COMMAND);
		return response;
	}

}
