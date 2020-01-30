package by.training.finance.exception;

public class WrongConfirmPasswordException extends ServiceException {

	private static final long serialVersionUID = 6278479101369428794L;

	public WrongConfirmPasswordException() {
	}

	public WrongConfirmPasswordException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public WrongConfirmPasswordException(String arg0) {
		super(arg0);
	}

	public WrongConfirmPasswordException(Throwable arg0) {
		super(arg0);
	}

	
}
