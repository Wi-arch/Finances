package by.training.finance.controller.command;

public class ResponseParser {

	private final static String DEFAULT_RESPONSE = "<Status503>";

	public static String getResponseStatus(Throwable e) {

		if (e == null) {
			return DEFAULT_RESPONSE;
		}
		String status = getSourceCause(e).toString();
		return status.matches(".*<Status\\d+>.*") ? status.replaceAll(".*(<Status\\d+>).*", "$1") : DEFAULT_RESPONSE;
	}

	private static Throwable getSourceCause(Throwable e) {
		Throwable cause = null;
		Throwable result = e;
		while ((cause = result.getCause()) != null && (result != cause)) {
			result = cause;
		}
		return result;
	}
}
