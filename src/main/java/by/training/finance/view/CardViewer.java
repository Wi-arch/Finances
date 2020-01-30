package by.training.finance.view;

import static by.training.finance.manager.MessageManager.getMessage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import by.training.finance.bean.Card;
import by.training.finance.bean.Currency;
import by.training.finance.bean.Transaction;
import by.training.finance.command.CommandNameManager;
import by.training.finance.controller.Controller;

public class CardViewer {

	private final static Controller CONTROLLER = Controller.getInstance();
	private final static Scanner SCANNER = new Scanner(System.in);
	private final static String SUCCESSFUL_RESPONSE = "<Status200>";

	public static void showCardOperations() {

		while (true) {
			System.out.println(getMessage("menu.cardOperations"));
			String choice = SCANNER.nextLine();
			switch (choice) {
			case "1":
				createCard();
				break;
			case "2":
				showAllCards();
				break;
			case "3":
				return;
			default:
				System.out.println(getMessage("message.wrongCommand"));
			}
		}
	}

	private static void createCard() {

		System.out.println(getMessage("message.enterCardNumber"));
		Long cardNumber = Long.valueOf(0);
		try {
			cardNumber = Long.valueOf(SCANNER.nextLine());
		} catch (NumberFormatException e) {
			System.out.println(getMessage("message.invalidCardNumber"));
			return;
		}
		System.out.println(getMessage("message.enterCurrency"));
		String currencyChoice = SCANNER.nextLine();
		Currency currency = null;
		try {
			currency = Currency.valueOf(currencyChoice.toUpperCase());
		} catch (IllegalArgumentException e) {
			System.out.println(getMessage("message.invalidCurrency"));
			return;
		}

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.CREATE_CARD);
		request.put("login", UserViewer.getSessionLogin());
		request.put("cardNumber", cardNumber);
		request.put("currency", currency);

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("card.create.ok"));
		} else {
			System.out.println(getMessage(answer));
		}
	}

	@SuppressWarnings("unchecked")
	private static void showAllCards() {

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.GET_CARD_BY_HOLDER);
		request.put("login", UserViewer.getSessionLogin());

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {

			List<Card> cards = (List<Card>) response.get("cards");
			if (!cards.isEmpty()) {
				iterateOverCards(cards);
			} else {
				System.out.println(getMessage("message.noCards"));
			}
		} else {
			System.out.println(getMessage(answer));
		}
	}

	private static void lockCard(Card card) {

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.LOCK_CARD);
		request.put("cardNumber", card.getNumber());

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("card.lock.ok"));
		} else {
			System.out.println(getMessage(answer));
		}
	}

	private static void unlockCard(Card card) {

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.UNLOCK_CARD);
		request.put("cardNumber", card.getNumber());

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("card.unlock.ok"));
		} else {
			System.out.println(getMessage(answer));
		}
	}

	private static void makePayment(Card card) {

		System.out.println(getMessage("message.enterAmountOfPayment"));
		String amountChoice = SCANNER.nextLine();
		BigDecimal amount = null;
		try {
			amount = new BigDecimal(amountChoice);
		} catch (IllegalArgumentException e) {
			System.out.println(getMessage("message.invalidAmount"));
			return;
		}

		System.out.println(getMessage("message.enterCurrencyOfPayment"));
		String currencyChoice = SCANNER.nextLine();
		Currency currency = null;
		try {
			currency = Currency.valueOf(currencyChoice.toUpperCase());
		} catch (IllegalArgumentException e) {
			System.out.println(getMessage("message.invalidCurrency"));
			return;
		}

		System.out.println(getMessage("message.enterPaymentPurpose"));
		String paymentPurpose = SCANNER.nextLine();

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.MAKE_CARD_PAYMENT);
		request.put("cardNumber", card.getNumber());
		request.put("amount", amount);
		request.put("currency", currency);
		request.put("paymentPurpose", paymentPurpose);

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("card.payment.ok"));
		} else {
			System.out.println(getMessage(answer));
		}
	}

	private static void rechargeCard(Card card) {

		System.out.println(getMessage("message.enterAmountToBeCredited"));
		String amountChoice = SCANNER.nextLine();
		BigDecimal amount = null;
		try {
			amount = new BigDecimal(amountChoice);
		} catch (IllegalArgumentException e) {
			System.out.println(getMessage("message.invalidAmount"));
			return;
		}

		System.out.println(getMessage("message.enterCurrencyOfRecharge"));
		String currencyChoice = SCANNER.nextLine();
		Currency currency = null;
		try {
			currency = Currency.valueOf(currencyChoice.toUpperCase());
		} catch (IllegalArgumentException e) {
			System.out.println(getMessage("message.invalidCurrency"));
			return;
		}

		System.out.println(getMessage("message.enterPaymentPurpose"));
		String paymentPurpose = SCANNER.nextLine();

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.RECHARGE_CARD);
		request.put("cardNumber", card.getNumber());
		request.put("amount", amount);
		request.put("currency", currency);
		request.put("paymentPurpose", paymentPurpose);

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("card.recharge.ok"));
		} else {
			System.out.println(getMessage(answer));
		}
	}

	private static void makeTransfer(Card card) {

		System.out.println(getMessage("message.enterReceiverCardNumber"));
		Long receiverCardNumber = Long.valueOf(0);
		try {
			receiverCardNumber = Long.valueOf(SCANNER.nextLine());
		} catch (NumberFormatException e) {
			System.out.println(getMessage("message.invalidCardNumber"));
			return;
		}

		System.out.println(getMessage("message.enterAmountOfTransfer"));
		String amountChoice = SCANNER.nextLine();
		BigDecimal amount = null;
		try {
			amount = new BigDecimal(amountChoice);
		} catch (IllegalArgumentException e) {
			System.out.println(getMessage("message.invalidAmount"));
			return;
		}

		System.out.println(getMessage("message.enterCurrencyOfTransfer"));
		String currencyChoice = SCANNER.nextLine();
		Currency currency = null;
		try {
			currency = Currency.valueOf(currencyChoice.toUpperCase());
		} catch (IllegalArgumentException e) {
			System.out.println(getMessage("message.invalidCurrency"));
			return;
		}

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.MAKE_CARD_TRANSFER);
		request.put("senderCardNumber", card.getNumber());
		request.put("receiverCardNumber", receiverCardNumber);
		request.put("amount", amount);
		request.put("currency", currency);

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("card.transfer.ok"));
		} else {
			System.out.println(getMessage(answer));
		}
	}

	private static void iterateOverCards(List<Card> cards) {

		int i = 0;
		printCard(cards.get(i));
		while (true) {
			System.out.println(getMessage("menu.iterateOverCards"));
			String choice = SCANNER.nextLine();
			switch (choice) {
			case "1":
				i = i < cards.size() - 1 ? ++i : 0;
				printCard(cards.get(i));
				break;
			case "2":
				i = i > 0 ? --i : cards.size() - 1;
				printCard(cards.get(i));
				break;
			case "3":
				lockCard(cards.get(i));
				return;
			case "4":
				unlockCard(cards.get(i));
				return;
			case "5":
				rechargeCard(cards.get(i));
				return;
			case "6":
				makePayment(cards.get(i));
				return;
			case "7":
				makeTransfer(cards.get(i));
				return;
			case "8":
				showAllTransactions(cards.get(i));
				return;
			case "9":
				deleteCard(cards.get(i));
				return;
			case "0":
				return;
			default:
				System.out.println(getMessage("message.wrongCommand"));
			}
		}
	}

	private static void showAllTransactions(Card card) {

		while (true) {
			System.out.println(getMessage("menu.transactionsMenu"));
			String choice = SCANNER.nextLine();
			switch (choice) {
			case "1":
				showAllWriteOffTransactions(card);
				break;
			case "2":
				showAllCreditTransactions(card);
				break;
			case "3":
				return;
			default:
				System.out.println(getMessage("message.wrongCommand"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	private static void showAllCreditTransactions(Card card) {

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.GET_ALL_CREDIT_TRANSACTIONS);
		request.put("cardNumber", card.getNumber());

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {

			List<Transaction> transactions = (List<Transaction>) response.get("transactions");
			if (!transactions.isEmpty()) {
				showTransactions(transactions);
			} else {
				System.out.println(getMessage("message.noCreditTransactions"));
			}
		} else {
			System.out.println(getMessage(answer));
		}
	}

	@SuppressWarnings("unchecked")
	private static void showAllWriteOffTransactions(Card card) {

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.GET_ALL_WRITEOFF_TRANSACTIONS);
		request.put("cardNumber", card.getNumber());

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {

			List<Transaction> transactions = (List<Transaction>) response.get("transactions");
			if (!transactions.isEmpty()) {
				showTransactions(transactions);
			} else {
				System.out.println(getMessage("message.noWriteOffTransactions"));
			}
		} else {
			System.out.println(getMessage(answer));
		}
	}

	private static void showTransactions(List<Transaction> transactions) {

		for (Transaction transaction : transactions) {
			System.out.print(getMessage("message.cardNumber") + transaction.getCardNumber());
			System.out.print(
					" " + getMessage("message.amount") + transaction.getAmount().setScale(2, RoundingMode.HALF_UP));
			System.out.print(" " + transaction.getCurrency());
			System.out.print(" " + getMessage("message.paymentPurpose") + transaction.getPaymentPurpose());
			System.out.print(", " + getMessage("message.operationDate") + transaction.getDate());
			if (transaction.isWriteOffOperation()) {
				System.out.print(", " + getMessage("message.writeOffTransaction"));
			} else {
				System.out.print(", " + getMessage("message.creditTransaction"));
			}
			if (transaction.isCompleted()) {
				System.out.println(", " + getMessage("message.transactionCompleted"));
			} else {
				System.out.println(", " + getMessage("message.transactionFailed"));
			}
		}
	}

	private static void deleteCard(Card card) {

		System.out.println(getMessage("message.cardDeletionConfirmation"));
		String ConfirmAnswer = SCANNER.nextLine();
		if (!ConfirmAnswer.equalsIgnoreCase("Yes")) {
			return;
		}

		Map<String, Object> request = new HashMap<>();
		request.put("command", CommandNameManager.DELETE_CARD);
		request.put("cardNumber", card.getNumber());

		Map<String, Object> response = CONTROLLER.execute(request);
		String answer = (String) response.get("response");

		if (answer.equals(SUCCESSFUL_RESPONSE)) {
			System.out.println(getMessage("card.delete.ok"));
		} else {
			System.out.println(getMessage(answer));
		}
	}

	private static void printCard(Card card) {

		System.out.print(getMessage("message.cardNumber") + card.getNumber());
		System.out.print(
				getMessage("message.balanceAfterTabulation") + card.getBalance().setScale(2, RoundingMode.HALF_UP));
		System.out.print(" " + card.getCurrency());
		if (card.isLock()) {
			System.out.println(getMessage("message.cardLockedAfterTabulation"));
		} else {
			System.out.println(getMessage("message.cardActiveAfterTabulation"));
		}
	}

}
