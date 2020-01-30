package by.training.finance.exception;

public class NotExistingUserException extends ServiceException {

	private static final long serialVersionUID = 7798600644145666163L;

	public NotExistingUserException() {
	}

	public NotExistingUserException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotExistingUserException(String arg0) {
		super(arg0);
	}

	public NotExistingUserException(Throwable arg0) {
		super(arg0);
	}

}
