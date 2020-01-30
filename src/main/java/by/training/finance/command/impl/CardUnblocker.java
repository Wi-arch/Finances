package by.training.finance.command.impl;

import java.util.HashMap;
import java.util.Map;

import by.training.finance.command.Command;
import by.training.finance.command.ResponseParser;
import by.training.finance.exception.ServiceException;
import by.training.finance.factory.ServiceFactory;
import by.training.finance.service.CardService;

public class CardUnblocker implements Command {

	private final static String SUCCESSFUL_RESPONSE = "<Status200>";

	@Override
	public Map<String, Object> execute(Map<String, Object> request) {

		Long number = Long.valueOf(0);
		if (request.get("cardNumber") instanceof Long) {
			number = Long.valueOf((Long) request.get("cardNumber"));
		}

		ServiceFactory factory = ServiceFactory.getInstance();
		CardService cardService = factory.getCardService();
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			cardService.unlockCard(number);
			response.put("response", SUCCESSFUL_RESPONSE);

		} catch (ServiceException e) {
			// write log
			response.put("response", ResponseParser.getResponseStatus(e));
		}
		return response;
	}

}
