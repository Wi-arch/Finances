package by.training.finance.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import by.training.finance.bean.Card;
import by.training.finance.dao.CardDAO;
import by.training.finance.dao.exception.DAOException;

public class FileCardDAO implements CardDAO {

	private final static String PATH = "card.out";

	@Override
	public synchronized void saveCard(Card card) throws DAOException {

		if (isNull(card)) {
			throw new DAOException("Card or its values are not initialized");
		}
		List<Card> cards = getAllCards();
		Integer index = getIndexOfCard(cards, card.getNumber());
		if (index != null) {
			throw new DAOException("Primary key " + card.getNumber() + " already exists");
		}
		cards.add(card);
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(cards);
		} catch (IOException e) {
			throw new DAOException("Exception while saving card", e);
		}
	}

	@Override
	public synchronized Card getCardByNumber(long number) throws DAOException {

		List<Card> cards = getAllCards();
		Integer index = getIndexOfCard(cards, number);
		return index == null ? null : cards.get(index.intValue());
	}

	@Override
	public synchronized List<Card> getCardsByHolder(String login) throws DAOException {

		if (login == null) {
			throw new DAOException("Login is not initialized");
		}
		List<Card> cards = getAllCards();
		List<Card> result = new ArrayList<Card>();
		for (Card card : cards) {
			if (card.getHolder().getLogin().equals(login)) {
				result.add(card);
			}
		}
		return result;
	}

	@Override
	public synchronized void updateCard(Card card) throws DAOException {

		if (isNull(card)) {
			throw new DAOException("Cannot update uninitialized card");
		}
		List<Card> cards = getAllCards();
		Integer index = getIndexOfCard(cards, card.getNumber());
		if (index == null) {
			throw new DAOException("Cannot update non existing card");
		}
		cards.set(index, card);

		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(cards);
		} catch (IOException e) {
			throw new DAOException("Exception while updating card", e);
		}
	}

	@Override
	public synchronized void saveTransfer(Card sender, Card receiver) throws DAOException {

		if (isNull(sender) || isNull(receiver)) {
			throw new DAOException("Card or its values are not initialized");
		}
		List<Card> cards = getAllCards();
		Integer senderIndex = getIndexOfCard(cards, sender.getNumber());
		Integer receiverindex = getIndexOfCard(cards, receiver.getNumber());

		if (senderIndex == null || receiverindex == null) {
			throw new DAOException("Cannot update non existing card");
		}
		cards.set(senderIndex, sender);
		cards.set(receiverindex, receiver);

		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(cards);
		} catch (IOException e) {
			throw new DAOException("Exception while saving transfer", e);
		}

	}

	@Override
	public synchronized void deleteCard(long number) throws DAOException {

		List<Card> cards = getAllCards();
		Integer index = getIndexOfCard(cards, number);
		if (index == null) {
			throw new DAOException("Cannot delete non existing card");
		}
		cards.remove(index.intValue());
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(cards);
		} catch (IOException e) {
			throw new DAOException("Exception while delete card", e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized List<Card> getAllCards() throws DAOException {

		if (!new File(PATH).exists()) {
			init();
		}
		List<Card> cards = null;
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(PATH))) {
			cards = (List<Card>) reader.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new DAOException("Exception while read card", e);
		}
		return cards;
	}

	private void init() throws DAOException {

		List<Card> cards = new ArrayList<Card>();
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(cards);
		} catch (IOException e) {
			throw new DAOException("Exception creating file " + PATH, e);
		}
	}

	private Integer getIndexOfCard(List<Card> cards, long number) {

		if (cards != null) {
			for (int i = 0; i < cards.size(); i++) {
				if (cards.get(i).getNumber() == number) {
					return i;
				}
			}
		}
		return null;
	}

	private boolean isNull(Card card) {

		if (card == null || card.getBalance() == null || card.getCurrency() == null || card.getHolder() == null
				|| card.getNumber() == 0) {
			return true;
		}
		return false;
	}

}
