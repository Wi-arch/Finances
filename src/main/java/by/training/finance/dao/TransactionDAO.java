package by.training.finance.dao;

import java.util.List;

import by.training.finance.bean.Transaction;
import by.training.finance.dao.exception.DAOException;

public interface TransactionDAO {

	public void saveTransaction(Transaction transaction) throws DAOException;

	public List<Transaction> getAllCreditingTransactions(long cardNumber) throws DAOException;

	public List<Transaction> getAllWriteOffTransactions(long cardNumber) throws DAOException;

	public Transaction getTransactionById(long id) throws DAOException;

	public List<Transaction> getAllTransactions() throws DAOException;

}
