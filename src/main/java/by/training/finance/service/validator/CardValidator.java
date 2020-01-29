package by.training.finance.service.validator;

import java.math.BigDecimal;

import by.training.finance.bean.Card;

public class CardValidator {

	private final static int CARD_NUMBER_LENGTH = 16;
	private final static BigDecimal FIVE = new BigDecimal(5);
	private final static BigDecimal ZERO = new BigDecimal(0);

	public static boolean checkCardNumberLength(long number) {

		return getLength(number) == CARD_NUMBER_LENGTH;
	}

	public static boolean checkAmountForTransfer(BigDecimal amount) {

		return amount.compareTo(FIVE) == 1 || amount.compareTo(FIVE) == 0;
	}

	public static boolean isAmountPositive(BigDecimal amount) {

		return amount.compareTo(ZERO) == 1;
	}

	public static boolean checkEnoughMoney(Card card, BigDecimal amount) {

		int result = card.getBalance().compareTo(amount);

		return result == 1 || result == 0;
	}

	private static int getLength(long number) {

		int result = 1;
		while (number / 10 != 0) {
			number /= 10;
			result++;
		}
		return result;
	}
}
