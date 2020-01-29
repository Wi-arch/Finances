package by.training.finance.service.impl;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.List;

import by.training.finance.bean.Currency;
import by.training.finance.bean.Transaction;
import by.training.finance.dao.TransactionDAO;
import by.training.finance.dao.exception.DAOException;
import by.training.finance.dao.factory.DAOFactory;
import by.training.finance.service.TransactionService;
import by.training.finance.service.exception.ServiceException;
import by.training.finance.service.exception.cardservice.IllegalCardNumberException;
import by.training.finance.service.exception.cardservice.InvalidAmountException;
import by.training.finance.service.exception.cardservice.NotExistingCardException;
import by.training.finance.service.exception.cardservice.NullCurrencyException;
import by.training.finance.service.validator.CardValidator;

public class TransactionServiceImpl implements TransactionService {

	private final DAOFactory DAO_FACTORY = DAOFactory.getInstance();
	private final TransactionDAO TRANSACTION_DAO = DAO_FACTORY.getTransactionDAO();

	@Override
	public List<Transaction> getAllCreditingTransactions(long number) throws ServiceException {

		if (!CardValidator.checkCardNumberLength(number)) {
			throw new NotExistingCardException("Card does not exist");
		}
		List<Transaction> transactions = null;
		try {
			transactions = TRANSACTION_DAO.getAllCreditingTransactions(number);
		} catch (DAOException e) {
			throw new ServiceException("Cannot read transactions", e);
		}
		return transactions;
	}

	@Override
	public List<Transaction> getAllWriteOffTransactions(long number) throws ServiceException {

		if (!CardValidator.checkCardNumberLength(number)) {
			throw new NotExistingCardException("Card does not exist");
		}
		List<Transaction> transactions = null;
		try {
			transactions = TRANSACTION_DAO.getAllWriteOffTransactions(number);
		} catch (DAOException e) {
			throw new ServiceException("Cannot read transactions", e);
		}
		return transactions;
	}

	@Override
	public void saveTransaction(long cardNumber, String paymentPurpose, BigDecimal amount, Currency currency,
			boolean completed, boolean writeOffOperation) throws ServiceException {

		if (!CardValidator.checkCardNumberLength(cardNumber)) {
			throw new IllegalCardNumberException("Illegal card number length <Status3001>");
		}
		if (paymentPurpose == null) {
			throw new ServiceException("Payment purpose is not initialized <Status3005>");
		}
		if (amount == null) {
			throw new InvalidAmountException("Amount is not initialized <Status3007>");
		}
		if (currency == null) {
			throw new NullCurrencyException("Currency is not initialized <Status3004>");
		}
		Transaction transaction = new Transaction();
		transaction.setCardNumber(cardNumber);
		transaction.setPaymentPurpose(paymentPurpose);
		transaction.setAmount(amount);
		transaction.setCurrency(currency);
		transaction.setCompleted(completed);
		transaction.setWriteOffOperation(writeOffOperation);
		transaction.setDate(new GregorianCalendar().getTime());

		try {
			TRANSACTION_DAO.saveTransaction(transaction);
		} catch (DAOException e) {
			throw new ServiceException("Cannot save transaction", e);
		}
	}

}
