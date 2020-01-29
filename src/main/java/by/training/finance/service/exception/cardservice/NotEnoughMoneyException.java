package by.training.finance.service.exception.cardservice;

import by.training.finance.service.exception.ServiceException;

public class NotEnoughMoneyException extends ServiceException {

	private static final long serialVersionUID = 288460982199958347L;

	public NotEnoughMoneyException() {
	}

	public NotEnoughMoneyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotEnoughMoneyException(String arg0) {
		super(arg0);
	}

	public NotEnoughMoneyException(Throwable arg0) {
		super(arg0);
	}

}
