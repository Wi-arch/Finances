package by.training.finance.factory;

import by.training.finance.dao.impl.*;

public class DAOFactory {

	private final static DAOFactory instance = new DAOFactory();

	private final FileCardDAO CardDAO = new FileCardDAO();
	private final FileMessageDAO MessageDAO = new FileMessageDAO();
	private final FileTransactionDAO TransactionDAO = new FileTransactionDAO();
	private final FileUserDAO UserDAO = new FileUserDAO();

	private DAOFactory() {
	}

	public static DAOFactory getInstance() {
		return instance;
	}

	public FileCardDAO getCardDAO() {
		return CardDAO;
	}

	public FileMessageDAO getMessageDAO() {
		return MessageDAO;
	}

	public FileTransactionDAO getTransactionDAO() {
		return TransactionDAO;
	}

	public FileUserDAO getUserDAO() {
		return UserDAO;
	}

}
