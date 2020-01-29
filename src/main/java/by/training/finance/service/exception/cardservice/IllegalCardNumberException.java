package by.training.finance.service.exception.cardservice;

import by.training.finance.service.exception.ServiceException;

public class IllegalCardNumberException extends ServiceException {

	private static final long serialVersionUID = 2466245688753822964L;

	public IllegalCardNumberException() {
	}

	public IllegalCardNumberException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IllegalCardNumberException(String arg0) {
		super(arg0);
	}

	public IllegalCardNumberException(Throwable arg0) {
		super(arg0);
	}

}
