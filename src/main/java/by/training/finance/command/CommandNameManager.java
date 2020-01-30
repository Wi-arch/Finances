package by.training.finance.command;

import by.training.finance.command.impl.*;

public enum CommandNameManager {

	REGISTRATION(new UserRegistrator()),
	SIGN_IN (new UserSignIn()),
	LOG_OUT(new UserLogOut()),
	UPDATE_PASSWORD (new UserPasswordUpdater()),
	DELETE_USER(new UserRemover()),
	GET_ALL_CREDIT_TRANSACTIONS (new CreditTransactionReceiver()),
	GET_ALL_WRITEOFF_TRANSACTIONS(new WriteOffTransactionReceiver()),
	SEND_MESSAGE (new MessageSender()),
	DELETE_MESSAGE(new MessageRemover()),
	GET_ALL_INPUT_MESSAGES(new InputMessagesReceiver()),
	GET_ALL_OUTPUT_MESSAGES(new OutputMessagesReceiver()),
	CREATE_CARD(new CardMaker()),
	DELETE_CARD(new CardRemover()),
	LOCK_CARD(new CardBlocker()),
	UNLOCK_CARD(new CardUnblocker()),
	GET_CARD_BY_NUMBER(new CardReceiverByNumber()),
	GET_CARD_BY_HOLDER(new CardReceiverByHolder()),
	RECHARGE_CARD(new CardCreditor()),
	MAKE_CARD_PAYMENT(new CardPayer()),
	MAKE_CARD_TRANSFER(new CardTransfer()),
	WRONG_REQUEST(new WrongRequest());

	private CommandNameManager(Command command) {
		this.command = command;
	}
	
	Command command;

	public Command getCommand() {
		return command;
	}

}
