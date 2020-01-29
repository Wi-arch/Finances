package by.training.finance.controller.command.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import by.training.finance.bean.Currency;
import by.training.finance.controller.command.Command;
import by.training.finance.controller.command.ResponseParser;
import by.training.finance.service.CardService;
import by.training.finance.service.exception.ServiceException;
import by.training.finance.service.factory.ServiceFactory;

public class CardCreditor implements Command {

	private final static String SUCCESSFUL_RESPONSE = "<Status200>";

	@Override
	public Map<String, Object> execute(Map<String, Object> request) {

		Long number = Long.valueOf(0);
		if (request.get("cardNumber") instanceof Long) {
			number = Long.valueOf((Long) request.get("cardNumber"));
		}
		String paymentPurpose = null;
		if (request.get("paymentPurpose") instanceof String) {
			paymentPurpose = (String) request.get("paymentPurpose");
		}
		Currency currency = null;
		if (request.get("currency") instanceof Currency) {
			currency = (Currency) request.get("currency");
		}
		BigDecimal amount = null;
		if (request.get("amount") instanceof BigDecimal) {
			amount = (BigDecimal) request.get("amount");
		}

		ServiceFactory factory = ServiceFactory.getInstance();
		CardService cardService = factory.getCardService();
		Map<String, Object> response = new HashMap<String, Object>();

		try {
			cardService.rechargeCard(number, amount, currency, paymentPurpose);
			response.put("response", SUCCESSFUL_RESPONSE);

		} catch (ServiceException e) {
			// write log
			response.put("response", ResponseParser.getResponseStatus(e));
		}
		return response;
	}

}
