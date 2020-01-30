package by.training.finance.command.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import by.training.finance.bean.Card;
import by.training.finance.command.Command;
import by.training.finance.command.ResponseParser;
import by.training.finance.exception.ServiceException;
import by.training.finance.factory.ServiceFactory;
import by.training.finance.service.CardService;

public class CardReceiverByHolder implements Command {

	private final static String SUCCESSFUL_RESPONSE = "<Status200>";

	@Override
	public Map<String, Object> execute(Map<String, Object> request) {

		String login = null;
		if (request.get("login") instanceof String) {
			login = (String) request.get("login");
		}

		ServiceFactory factory = ServiceFactory.getInstance();
		CardService cardService = factory.getCardService();
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			List<Card> cards = cardService.getAllCardsByHolder(login);
			response.put("cards", cards);
			response.put("response", SUCCESSFUL_RESPONSE);

		} catch (ServiceException e) {
			// write log
			response.put("response", ResponseParser.getResponseStatus(e));
		}
		return response;
	}

}
