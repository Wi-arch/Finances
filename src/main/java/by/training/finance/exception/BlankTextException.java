package by.training.finance.exception;

public class BlankTextException extends ServiceException {

	private static final long serialVersionUID = -5941756610352417800L;

	public BlankTextException() {
	}

	public BlankTextException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public BlankTextException(String arg0) {
		super(arg0);
	}

	public BlankTextException(Throwable arg0) {
		super(arg0);
	}

}
