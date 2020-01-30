package by.training.finance.exception;

public class InvalidLoginException extends ServiceException {

	private static final long serialVersionUID = -8307977122112068494L;

	public InvalidLoginException() {
	}

	public InvalidLoginException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidLoginException(String arg0) {
		super(arg0);
	}

	public InvalidLoginException(Throwable arg0) {
		super(arg0);
	}

}
