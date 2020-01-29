package by.training.finance.service;

import java.math.BigDecimal;
import java.util.List;

import by.training.finance.bean.Card;
import by.training.finance.bean.Currency;
import by.training.finance.service.exception.ServiceException;

public interface CardService {

	public void transfer(long senderCardNumber, long receiverCardNumber, BigDecimal amount, Currency currency)
			throws ServiceException;

	public void createCard(long number, Currency currency, String holderLogin) throws ServiceException;

	public void deleteCard(long number) throws ServiceException;

	public void lockCard(long number) throws ServiceException;

	public void unlockCard(long number) throws ServiceException;

	public Card getCardByNumber(long number) throws ServiceException;

	public List<Card> getAllCardsByHolder(String login) throws ServiceException;

	public void rechargeCard(long cardNumber, BigDecimal amount, Currency currency, String paymentPurpose)
			throws ServiceException;

	public void makePayment(long cardNumber, BigDecimal amount, Currency currency, String paymentPurpose)
			throws ServiceException;

}
