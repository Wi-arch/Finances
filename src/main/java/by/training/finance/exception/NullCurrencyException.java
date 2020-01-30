package by.training.finance.exception;

public class NullCurrencyException extends ServiceException {

	private static final long serialVersionUID = 7646251052527278177L;

	public NullCurrencyException() {
	}

	public NullCurrencyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NullCurrencyException(String arg0) {
		super(arg0);
	}

	public NullCurrencyException(Throwable arg0) {
		super(arg0);
	}

}
