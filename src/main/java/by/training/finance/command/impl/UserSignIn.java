package by.training.finance.command.impl;

import java.util.HashMap;
import java.util.Map;

import by.training.finance.command.Command;
import by.training.finance.command.ResponseParser;
import by.training.finance.exception.ServiceException;
import by.training.finance.factory.ServiceFactory;
import by.training.finance.service.UserService;

public class UserSignIn implements Command {

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
			userService.logIn(login, password);
			response.put("response", SUCCESSFUL_RESPONSE);

		} catch (ServiceException e) {
			// write log
			response.put("response", ResponseParser.getResponseStatus(e));
		}
		return response;
	}

}
