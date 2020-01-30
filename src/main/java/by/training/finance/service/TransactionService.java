package by.training.finance.service;

import java.math.BigDecimal;
import java.util.List;

import by.training.finance.bean.Currency;
import by.training.finance.bean.Transaction;
import by.training.finance.exception.ServiceException;

public interface TransactionService {

	public void saveTransaction(long cardNumber, String paymentPurpose, BigDecimal amount, Currency currency,
			boolean completed, boolean writeOffOperation) throws ServiceException;

	public List<Transaction> getAllCreditingTransactions(long number) throws ServiceException;

	public List<Transaction> getAllWriteOffTransactions(long number) throws ServiceException;

}
