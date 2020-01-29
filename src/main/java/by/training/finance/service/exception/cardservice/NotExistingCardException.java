package by.training.finance.service.exception.cardservice;

import by.training.finance.service.exception.ServiceException;

public class NotExistingCardException extends ServiceException {

	private static final long serialVersionUID = -3993867598420633818L;

	public NotExistingCardException() {
	}

	public NotExistingCardException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotExistingCardException(String arg0) {
		super(arg0);
	}

	public NotExistingCardException(Throwable arg0) {
		super(arg0);
	}

}
