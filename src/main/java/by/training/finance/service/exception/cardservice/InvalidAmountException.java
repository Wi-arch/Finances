package by.training.finance.service.exception.cardservice;

import by.training.finance.service.exception.ServiceException;

public class InvalidAmountException extends ServiceException {

	private static final long serialVersionUID = 3408789194606155463L;

	public InvalidAmountException() {
	}

	public InvalidAmountException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidAmountException(String arg0) {
		super(arg0);
	}

	public InvalidAmountException(Throwable arg0) {
		super(arg0);
	}

}
