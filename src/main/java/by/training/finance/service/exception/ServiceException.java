package by.training.finance.service.exception;

import by.training.finance.dao.exception.DAOException;

@SuppressWarnings("serial")
public class ServiceException extends DAOException {

	public ServiceException() {
	}

	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ServiceException(String arg0) {
		super(arg0);
	}

	public ServiceException(Throwable arg0) {
		super(arg0);
	}
}
