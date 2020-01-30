package by.training.finance.exception;

public class IncorrectPasswordException extends ServiceException {

	private static final long serialVersionUID = 3232383256937739536L;

	public IncorrectPasswordException() {
	}

	public IncorrectPasswordException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public IncorrectPasswordException(String arg0) {
		super(arg0);
	}

	public IncorrectPasswordException(Throwable arg0) {
		super(arg0);
	}

}
