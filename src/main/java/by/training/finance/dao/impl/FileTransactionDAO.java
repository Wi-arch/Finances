package by.training.finance.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import by.training.finance.bean.Transaction;
import by.training.finance.bean.User;
import by.training.finance.dao.TransactionDAO;
import by.training.finance.dao.exception.DAOException;

public class FileTransactionDAO implements TransactionDAO {

	private final static String PATH = "transaction.out";

	@Override
	public synchronized void saveTransaction(Transaction transaction) throws DAOException {

		if (isNull(transaction)) {
			throw new DAOException("Transaction or its values are not initialized");
		}
		List<Transaction> transactions = getAllTransactions();
		transaction.setId(transactions.size());

		transactions.add(transaction);

		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(transactions);

		} catch (IOException e) {
			throw new DAOException("Exception while saving transactions", e);
		}
	}

	@Override
	public synchronized List<Transaction> getAllCreditingTransactions(long cardNumber) throws DAOException {

		List<Transaction> transactions = getAllTransactions();
		List<Transaction> result = new ArrayList<Transaction>();
		for (Transaction t : transactions) {
			if (t.getCardNumber() == cardNumber && !t.isWriteOffOperation()) {
				result.add(t);
			}
		}
		return result;
	}

	@Override
	public synchronized List<Transaction> getAllWriteOffTransactions(long cardNumber) throws DAOException {

		List<Transaction> transactions = getAllTransactions();
		List<Transaction> result = new ArrayList<Transaction>();
		for (Transaction t : transactions) {
			if (t.getCardNumber() == cardNumber && t.isWriteOffOperation()) {
				result.add(t);
			}
		}
		return result;
	}

	@Override
	public synchronized Transaction getTransactionById(long id) throws DAOException {

		List<Transaction> transactions = getAllTransactions();
		for (Transaction t : transactions) {
			if (t.getId() == id) {
				return t;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized List<Transaction> getAllTransactions() throws DAOException {

		if (!new File(PATH).exists()) {
			init();
		}
		List<Transaction> transactions = null;
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(PATH))) {
			transactions = (List<Transaction>) reader.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new DAOException("Exception while read transaction", e);
		}
		return transactions;
	}

	private void init() throws DAOException {

		List<User> users = new ArrayList<User>();
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(users);
		} catch (IOException e) {
			throw new DAOException("Exception creating file " + PATH, e);
		}
	}

	private boolean isNull(Transaction transaction) {

		if (transaction == null || transaction.getAmount() == null || transaction.getCurrency() == null
				|| transaction.getDate() == null || transaction.getCardNumber() == 0
				|| transaction.getPaymentPurpose() == null) {
			return true;
		}
		return false;
	}

}
