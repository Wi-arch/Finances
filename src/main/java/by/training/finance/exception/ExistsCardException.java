package by.training.finance.exception;

public class ExistsCardException extends ServiceException {

	private static final long serialVersionUID = 4879627644902679503L;

	public ExistsCardException() {
	}

	public ExistsCardException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ExistsCardException(String arg0) {
		super(arg0);
	}

	public ExistsCardException(Throwable arg0) {
		super(arg0);
	}

}
