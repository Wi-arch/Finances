package by.training.finance.exception;

public class InvalidPasswordException extends ServiceException {

	private static final long serialVersionUID = 5251673511277523003L;

	public InvalidPasswordException() {
	}

	public InvalidPasswordException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidPasswordException(String arg0) {
		super(arg0);
	}

	public InvalidPasswordException(Throwable arg0) {
		super(arg0);
	}

}
