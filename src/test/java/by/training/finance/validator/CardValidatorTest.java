package by.training.finance.validator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Test;

import by.training.finance.bean.Card;

public class CardValidatorTest {

	@Test
	public void testCardNumberLengthPositive() {

		long cardNumber = 5634_1243_9876_4653L;
		boolean actual = CardValidator.checkCardNumberLength(cardNumber);
		assertEquals(actual, true);
	}

	@Test
	public void testCardNumberLengthNegative() {

		long cardNumber = 12345;
		boolean actual = CardValidator.checkCardNumberLength(cardNumber);
		assertEquals(actual, false);
	}

	@Test
	public void testEnoughMoneyPositive() {

		Card card = new Card();
		card.setBalance(new BigDecimal(100));
		BigDecimal amount = new BigDecimal(50);
		boolean actual = CardValidator.checkEnoughMoney(card, amount);
		assertEquals(actual, true);
	}

	@Test
	public void testEnoughMoneyNegative() {

		Card card = new Card();
		card.setBalance(new BigDecimal(25));
		BigDecimal amount = new BigDecimal(50);
		boolean actual = CardValidator.checkEnoughMoney(card, amount);
		assertEquals(actual, false);
	}
}
