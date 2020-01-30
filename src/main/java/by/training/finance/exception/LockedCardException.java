package by.training.finance.exception;

public class LockedCardException extends ServiceException {

	private static final long serialVersionUID = 2629159393011989621L;

	public LockedCardException() {
	}

	public LockedCardException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public LockedCardException(String arg0) {
		super(arg0);
	}

	public LockedCardException(Throwable arg0) {
		super(arg0);
	}

}
