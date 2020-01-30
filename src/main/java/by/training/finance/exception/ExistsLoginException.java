package by.training.finance.exception;

public class ExistsLoginException extends ServiceException {

	private static final long serialVersionUID = 1L;

	public ExistsLoginException() {
	}

	public ExistsLoginException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ExistsLoginException(String arg0) {
		super(arg0);
	}

	public ExistsLoginException(Throwable arg0) {
		super(arg0);
	}

}
