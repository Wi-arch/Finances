package by.training.finance.dao;

import java.util.List;

import by.training.finance.bean.Message;
import by.training.finance.dao.exception.DAOException;

public interface MessageDAO {

	public void saveMessage(Message message) throws DAOException;

	public void deleteMessage(long id) throws DAOException;

	public List<Message> getMessagesBySender(String sender) throws DAOException;

	public List<Message> getMessagesByReceiver(String receiver) throws DAOException;

	public Message getMessageById(long id) throws DAOException;

	public List<Message> getAllMessages() throws DAOException;

}
