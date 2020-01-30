package by.training.finance.command.impl;

import java.util.HashMap;
import java.util.Map;

import by.training.finance.command.Command;
import by.training.finance.command.ResponseParser;
import by.training.finance.exception.ServiceException;
import by.training.finance.factory.ServiceFactory;
import by.training.finance.service.MessageService;

public class MessageRemover implements Command {

	private final static String SUCCESSFUL_RESPONSE = "<Status200>";

	@Override
	public Map<String, Object> execute(Map<String, Object> request) {

		Long id = null;
		if (request.get("id") instanceof String) {
			id = (Long) request.get("id");
		}

		ServiceFactory factory = ServiceFactory.getInstance();
		MessageService messageService = factory.getMessageService();
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			messageService.deleteMessage(id);
			response.put("response", SUCCESSFUL_RESPONSE);

		} catch (ServiceException e) {
			// write log
			response.put("response", ResponseParser.getResponseStatus(e));
		}
		return response;
	}

}
