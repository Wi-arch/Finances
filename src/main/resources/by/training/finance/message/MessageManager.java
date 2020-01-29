package by.training.finance.message;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageManager {

	private final static String PATH = "by.training.finance.message.messages";
	private final static Locale RUSSIAN_LOCALE = new Locale("ru", "RU");
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle(PATH);

	private MessageManager() {
	}

	public static String getMessage(String key) {
		return resourceBundle.getString(key);
	}

	public static void setRussianLocale() {
		resourceBundle = ResourceBundle.getBundle(PATH, RUSSIAN_LOCALE);
	}

	public static void setEnglishLocale() {
		resourceBundle = ResourceBundle.getBundle(PATH, Locale.ENGLISH);
	}	

}
