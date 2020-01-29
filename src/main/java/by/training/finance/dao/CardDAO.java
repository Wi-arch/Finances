package by.training.finance.dao;

import java.util.List;

import by.training.finance.bean.Card;
import by.training.finance.dao.exception.DAOException;

public interface CardDAO {

	public void saveCard(Card card) throws DAOException;

	public Card getCardByNumber(long number) throws DAOException;

	public List<Card> getCardsByHolder(String login) throws DAOException;

	public List<Card> getAllCards() throws DAOException;

	public void updateCard(Card card) throws DAOException;

	public void deleteCard(long number) throws DAOException;

	public void saveTransfer(Card sender, Card receiver) throws DAOException;
}
