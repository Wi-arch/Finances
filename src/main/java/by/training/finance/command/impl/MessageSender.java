package by.training.finance.command.impl;

import java.util.HashMap;
import java.util.Map;

import by.training.finance.command.Command;
import by.training.finance.command.ResponseParser;
import by.training.finance.exception.ServiceException;
import by.training.finance.factory.ServiceFactory;
import by.training.finance.service.MessageService;

public class MessageSender implements Command {

	private final static String SUCCESSFUL_RESPONSE = "<Status200>";

	@Override
	public Map<String, Object> execute(Map<String, Object> request) {

		String senderLogin = null;
		if (request.get("senderLogin") instanceof String) {
			senderLogin = (String) request.get("senderLogin");
		}
		String receiverLogin = null;
		if (request.get("receiverLogin") instanceof String) {
			receiverLogin = (String) request.get("receiverLogin");
		}
		String body = null;
		if (request.get("body") instanceof String) {
			body = (String) request.get("body");
		}

		ServiceFactory factory = ServiceFactory.getInstance();
		MessageService messageService = factory.getMessageService();
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			messageService.sendMessage(senderLogin, receiverLogin, body);
			response.put("response", SUCCESSFUL_RESPONSE);

		} catch (ServiceException e) {
			// write log
			response.put("response", ResponseParser.getResponseStatus(e));
		}
		return response;
	}

}
