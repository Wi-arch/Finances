package by.training.finance.controller.command.impl;

import java.util.HashMap;
import java.util.Map;

import by.training.finance.controller.command.Command;
import by.training.finance.controller.command.ResponseParser;
import by.training.finance.service.UserService;
import by.training.finance.service.exception.ServiceException;
import by.training.finance.service.factory.ServiceFactory;

public class UserRemover implements Command {

	private final static String SUCCESSFUL_RESPONSE = "<Status200>";

	@Override
	public Map<String, Object> execute(Map<String, Object> request) {

		String login = null;
		if (request.get("login") instanceof String) {
			login = (String) request.get("login");
		}
		String password = null;
		if (request.get("password") instanceof String) {
			password = (String) request.get("password");
		}
		ServiceFactory factory = ServiceFactory.getInstance();
		UserService userService = factory.getUserService();

		Map<String, Object> response = new HashMap<String, Object>();

		try {
			userService.deleteUser(login, password);
			response.put("response", SUCCESSFUL_RESPONSE);

		} catch (ServiceException e) {
			// write log
			response.put("response", ResponseParser.getResponseStatus(e));
		}
		return response;
	}

}
