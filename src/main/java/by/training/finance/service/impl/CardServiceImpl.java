package by.training.finance.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import by.training.finance.bean.Card;
import by.training.finance.bean.Currency;
import by.training.finance.bean.User;
import by.training.finance.dao.CardDAO;
import by.training.finance.dao.UserDAO;
import by.training.finance.dao.exception.DAOException;
import by.training.finance.dao.factory.DAOFactory;
import by.training.finance.message.MessageManager;
import by.training.finance.service.CardService;
import by.training.finance.service.TransactionService;
import by.training.finance.service.exception.ServiceException;
import by.training.finance.service.exception.cardservice.ExistsCardException;
import by.training.finance.service.exception.cardservice.IllegalCardNumberException;
import by.training.finance.service.exception.cardservice.InvalidAmountException;
import by.training.finance.service.exception.cardservice.LockedCardException;
import by.training.finance.service.exception.cardservice.NotEnoughMoneyException;
import by.training.finance.service.exception.cardservice.NotExistingCardException;
import by.training.finance.service.exception.cardservice.NullCurrencyException;
import by.training.finance.service.exception.userservice.NotExistingUserException;
import by.training.finance.service.factory.ServiceFactory;
import by.training.finance.service.validator.CardValidator;
import by.training.finance.service.validator.UserValidator;

public class CardServiceImpl implements CardService {

	private final DAOFactory DAO_FACTORY = DAOFactory.getInstance();
	private final CardDAO CARD_DAO = DAO_FACTORY.getCardDAO();

	@Override
	public void createCard(long number, Currency currency, String holderLogin) throws ServiceException {

		if (!CardValidator.checkCardNumberLength(number)) {
			throw new IllegalCardNumberException("Illegal card number length <Status3001>");
		}
		if (currency == null) {
			throw new NullCurrencyException("Currency is not initialized <Status3004>");
		}
		if (!UserValidator.checkLogin(holderLogin)) {
			throw new NotExistingUserException("User does not exist <Status1005>");
		}
		try {
			Card tempCard = CARD_DAO.getCardByNumber(number);
			if (tempCard != null) {
				throw new ExistsCardException("Card " + number + " already exist in the system <Status3013>");
			}
			UserDAO userDAO = DAO_FACTORY.getUserDAO();
			User user = userDAO.getUser(holderLogin);
			if (user == null) {
				throw new NotExistingUserException("User does not exist <Status1005>");
			}
			Card card = new Card();
			card.setBalance(new BigDecimal(0));
			card.setCurrency(currency);
			card.setHolder(user);
			card.setNumber(number);
			CARD_DAO.saveCard(card);

		} catch (DAOException e) {
			throw new ServiceException("Cannot create card", e);
		}
	}

	@Override
	public void deleteCard(long number) throws ServiceException {

		if (!CardValidator.checkCardNumberLength(number)) {
			throw new IllegalCardNumberException("Illegal card number length <Status3001>");
		}
		try {
			CARD_DAO.deleteCard(number);
		} catch (DAOException e) {
			throw new ServiceException("Cannot delete card", e);
		}
	}

	@Override
	public void lockCard(long number) throws ServiceException {

		if (!CardValidator.checkCardNumberLength(number)) {
			throw new IllegalCardNumberException("Illegal card number length <Status3001>");
		}
		try {
			Card card = CARD_DAO.getCardByNumber(number);
			if (card == null) {
				throw new NotExistingCardException("Card does not exist");
			}
			if (card.isLock()) {
				throw new LockedCardException("Card already locked <Status3003>");
			}
			card.setLock(true);
			CARD_DAO.updateCard(card);

		} catch (DAOException e) {
			throw new ServiceException("Cannot lock card", e);
		}
	}

	@Override
	public void unlockCard(long number) throws ServiceException {

		if (!CardValidator.checkCardNumberLength(number)) {
			throw new IllegalCardNumberException("Illegal card number length <Status3001>");
		}
		try {
			Card card = CARD_DAO.getCardByNumber(number);
			if (card == null) {
				throw new NotExistingCardException("Card does not exist");
			}
			card.setLock(false);
			CARD_DAO.updateCard(card);

		} catch (DAOException e) {
			throw new ServiceException("Cannot unlock card", e);
		}
	}

	@Override
	public Card getCardByNumber(long number) throws ServiceException {

		if (!CardValidator.checkCardNumberLength(number)) {
			throw new IllegalCardNumberException("Illegal card number length <Status3001>");
		}
		try {
			Card card = CARD_DAO.getCardByNumber(number);
			if (card == null) {
				throw new NotExistingCardException("Card does not exist");
			}
			return card;

		} catch (DAOException e) {
			throw new ServiceException("Cannot unlock card", e);
		}
	}

	@Override
	public List<Card> getAllCardsByHolder(String login) throws ServiceException {

		if (!UserValidator.checkLogin(login)) {
			throw new NotExistingUserException("User with login " + login + " does not exist");
		}
		List<Card> cards = null;
		try {
			cards = CARD_DAO.getCardsByHolder(login);
		} catch (DAOException e) {
			throw new ServiceException("Cannot read cards", e);
		}
		return cards;
	}

	@Override
	public void rechargeCard(long cardNumber, BigDecimal amount, Currency currency, String paymentPurpose)
			throws ServiceException {

		if (!CardValidator.checkCardNumberLength(cardNumber)) {
			throw new IllegalCardNumberException("Illegal card number length <Status3001>");
		}
		if (amount == null || !CardValidator.isAmountPositive(amount)) {
			throw new InvalidAmountException("Invalid recharge amount <Status3006>");
		}
		if (currency == null) {
			throw new NullCurrencyException("Currency is not initialized <Status3004>");
		}
		if (paymentPurpose == null || paymentPurpose.isBlank()) {
			throw new ServiceException("PaymentPurpose is not initialized <Status3005>");
		}
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		TransactionService transactionService = serviceFactory.getTransactionService();

		Card card = null;
		boolean completed = false;

		try {
			card = CARD_DAO.getCardByNumber(cardNumber);
			if (card == null) {
				throw new NotExistingCardException("Card does not exist");
			}
			if (card.isLock()) {
				throw new LockedCardException("Card is locked <Status3003>");
			}
			BigDecimal conversionAmount = conversion(amount, currency, card.getCurrency());
			card.setBalance(card.getBalance().add(conversionAmount));

			CARD_DAO.updateCard(card);
			completed = true;

		} catch (DAOException e) {
			throw new ServiceException("Card replenishment exception", e);
		} finally {
			try {
				transactionService.saveTransaction(cardNumber, paymentPurpose, amount, currency, completed, false);
			} catch (ServiceException e) {
				throw new ServiceException("Cannot save transaction", e);
			}
		}
	}

	@Override
	public void makePayment(long cardNumber, BigDecimal amount, Currency currency, String paymentPurpose)
			throws ServiceException {

		if (!CardValidator.checkCardNumberLength(cardNumber)) {
			throw new IllegalCardNumberException("Illegal card number length <Status3001>");
		}
		if (amount == null || !CardValidator.isAmountPositive(amount)) {
			throw new InvalidAmountException("Invalid recharge amount <Status3006>");
		}
		if (currency == null) {
			throw new NullCurrencyException("Currency is not initialized <Status3004>");
		}
		if (paymentPurpose == null || paymentPurpose.isBlank()) {
			throw new ServiceException("Payment purpose is not initialized <Status3005>");
		}
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		TransactionService transactionService = serviceFactory.getTransactionService();

		Card card = null;
		boolean completed = false;

		try {
			card = CARD_DAO.getCardByNumber(cardNumber);
			if (card == null) {
				throw new NotExistingCardException("Card does not exist");
			}
			if (card.isLock()) {
				throw new LockedCardException("Card is locked <Status3003>");
			}
			BigDecimal conversionAmount = conversion(amount, currency, card.getCurrency());

			if (!CardValidator.checkEnoughMoney(card, conversionAmount)) {
				throw new NotEnoughMoneyException("Not enough money to make a payment <Status3008>");
			}
			card.setBalance(card.getBalance().subtract(conversionAmount));

			CARD_DAO.updateCard(card);
			completed = true;

		} catch (DAOException e) {
			throw new ServiceException("Card replenishment exception", e);
		} finally {
			try {
				transactionService.saveTransaction(cardNumber, paymentPurpose, amount, currency, completed, true);
			} catch (ServiceException e) {
				throw new ServiceException("Cannot save transaction", e);
			}
		}

	}

	@Override
	public void transfer(long senderCardNumber, long receiverCardNumber, BigDecimal amount, Currency currency)
			throws ServiceException {

		if (!CardValidator.checkCardNumberLength(senderCardNumber)) {
			throw new IllegalCardNumberException("Illegal sender card number length <Status3001>");
		}
		if (!CardValidator.checkCardNumberLength(receiverCardNumber)) {
			throw new IllegalCardNumberException("Illegal receiver card number length <Status3002>");
		}
		if (senderCardNumber == receiverCardNumber) {
			throw new ServiceException("Same sender and receiver cards <Status3009>");
		}
		if (currency == null) {
			throw new NullCurrencyException("Currency is not initialized <Status3004>");
		}
		if (amount == null || !CardValidator.checkAmountForTransfer(conversion(amount, currency, Currency.BYN))) {
			throw new InvalidAmountException("Invalid amount of transfer <Status3010>");
		}

		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		TransactionService transactionService = serviceFactory.getTransactionService();

		String senderPaymentPurpose = MessageManager.getMessage("card.transfer.out") + receiverCardNumber;
		String receiverPaymentPurpose = MessageManager.getMessage("card.transfer.in") + senderCardNumber;
		Card senderCard = null;
		boolean completed = false;

		try {
			senderCard = CARD_DAO.getCardByNumber(senderCardNumber);
			if (senderCard == null) {
				throw new NotExistingCardException("Sender card not found");
			}
			if (senderCard.isLock()) {
				throw new LockedCardException("Sender card is locked <Status3003>");
			}
			Card receiverCard = CARD_DAO.getCardByNumber(receiverCardNumber);
			if (receiverCard == null) {
				throw new NotExistingCardException("Receiver card not found <Status3011>");
			}
			if (receiverCard.isLock()) {
				throw new LockedCardException("Receiver card is locked <Status3012>");
			}

			BigDecimal senderAmount = conversion(amount, currency, senderCard.getCurrency());
			BigDecimal receiverAmount = conversion(amount, currency, receiverCard.getCurrency());

			if (!CardValidator.checkEnoughMoney(senderCard, senderAmount)) {
				throw new NotEnoughMoneyException("Not enough money on the sender card <Status3012>");
			}

			senderCard.setBalance(senderCard.getBalance().subtract(senderAmount));
			receiverCard.setBalance(receiverCard.getBalance().add(receiverAmount));
			CARD_DAO.saveTransfer(senderCard, receiverCard);
			completed = true;
			transactionService.saveTransaction(receiverCardNumber, receiverPaymentPurpose, amount, currency, completed,
					false);

		} catch (DAOException e) {
			throw new ServiceException("Transfer not completed", e);

		} finally {
			try {
				transactionService.saveTransaction(senderCardNumber, senderPaymentPurpose, amount, currency, completed,
						true);
			} catch (ServiceException e) {
				throw new ServiceException("Cannot save transaction", e);
			}
		}
	}

	private BigDecimal conversion(BigDecimal amount, Currency from, Currency to) {

		if (from == to) {
			return amount;
		}
		BigDecimal rateFrom = new BigDecimal(from.getRate());
		BigDecimal rateTo = new BigDecimal(to.getRate());
		BigDecimal multiplicityTo = new BigDecimal(to.getCount());
		BigDecimal multiplicityFrom = new BigDecimal(from.getCount());

		return amount.multiply(multiplicityTo).multiply(rateFrom.divide(rateTo, 8, RoundingMode.HALF_UP))
				.divide(multiplicityFrom).setScale(2, RoundingMode.HALF_UP);
	}

}
