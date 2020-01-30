package by.training.finance.exception;

public class InvalidMessageReceiverException extends ServiceException {

	private static final long serialVersionUID = 8096862859799769329L;

	public InvalidMessageReceiverException() {
	}

	public InvalidMessageReceiverException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidMessageReceiverException(String arg0) {
		super(arg0);
	}

	public InvalidMessageReceiverException(Throwable arg0) {
		super(arg0);
	}

}
