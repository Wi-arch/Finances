package by.training.finance.service.factory;

import by.training.finance.service.CardService;
import by.training.finance.service.MessageService;
import by.training.finance.service.TransactionService;
import by.training.finance.service.UserService;
import by.training.finance.service.impl.CardServiceImpl;
import by.training.finance.service.impl.MessageServiceImpl;
import by.training.finance.service.impl.TransactionServiceImpl;
import by.training.finance.service.impl.UserServiceImpl;

public class ServiceFactory {

	private final static ServiceFactory instance = new ServiceFactory();

	private final MessageService messageService = new MessageServiceImpl();
	private final TransactionService transactionService = new TransactionServiceImpl();
	private final UserService userService = new UserServiceImpl();
	private final CardService cardService = new CardServiceImpl();

	private ServiceFactory() {

	}

	public static ServiceFactory getInstance() {
		return instance;
	}

	public CardService getCardService() {
		return cardService;
	}

	public UserService getUserService() {
		return userService;
	}

	public TransactionService getTransactionService() {
		return transactionService;
	}

	public MessageService getMessageService() {
		return messageService;
	}

}
