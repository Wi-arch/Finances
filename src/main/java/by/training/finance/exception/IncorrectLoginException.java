package by.training.finance.exception;

public class IncorrectLoginException extends ServiceException {

	private static final long serialVersionUID = -2077961136695676447L;

	public IncorrectLoginException() {
	}

	public IncorrectLoginException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IncorrectLoginException(String arg0) {
		super(arg0);
	}

	public IncorrectLoginException(Throwable arg0) {
		super(arg0);
	}

}
