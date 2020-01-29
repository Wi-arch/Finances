package by.training.finance.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import by.training.finance.bean.Message;
import by.training.finance.dao.MessageDAO;
import by.training.finance.dao.exception.DAOException;

public class FileMessageDAO implements MessageDAO {

	private final static String PATH = "message.out";

	@Override
	public synchronized void saveMessage(Message message) throws DAOException {

		if (isNull(message)) {
			throw new DAOException("Message or its values are not initialized");
		}
		List<Message> messages = getAllMessages();
		message.setId(messages.size());
		messages.add(message);

		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(messages);
		} catch (IOException e) {
			throw new DAOException("Exception while saving message", e);
		}
	}

	@Override
	public synchronized List<Message> getMessagesBySender(String sender) throws DAOException {

		List<Message> messages = getAllMessages();
		List<Message> result = new ArrayList<Message>();
		for (Message m : messages) {
			if (m.getSender().equals(sender)) {
				result.add(m);
			}
		}
		return result;
	}

	@Override
	public synchronized List<Message> getMessagesByReceiver(String receiver) throws DAOException {

		List<Message> messages = getAllMessages();
		List<Message> result = new ArrayList<Message>();
		for (Message m : messages) {
			if (m.getReceiver().equals(receiver)) {
				result.add(m);
			}
		}
		return result;
	}

	@Override
	public synchronized void deleteMessage(long id) throws DAOException {

		List<Message> messages = getAllMessages();
		Integer index = getIndexOfMessage(messages, id);
		if (index == null) {
			throw new DAOException("Cannot delete non existing message");
		}
		messages.remove(index.intValue());
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(messages);
		} catch (IOException e) {
			throw new DAOException("Exception while delete message", e);
		}
	}

	@Override
	public Message getMessageById(long id) throws DAOException {
		List<Message> messages = getAllMessages();
		Integer index = getIndexOfMessage(messages, id);
		return index == null ? null : messages.get(index.intValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized List<Message> getAllMessages() throws DAOException {

		if (!new File(PATH).exists()) {
			init();
		}
		List<Message> messages = null;
		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(PATH))) {
			messages = (List<Message>) reader.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new DAOException("Exception while read message", e);
		}
		return messages;
	}

	private void init() throws DAOException {

		List<Message> messages = new ArrayList<Message>();
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(messages);
		} catch (IOException e) {
			throw new DAOException("Exception creating file " + PATH, e);
		}
	}

	private Integer getIndexOfMessage(List<Message> messages, long id) {

		if (messages != null) {
			for (int i = 0; i < messages.size(); i++) {
				if (messages.get(i).getId() == id) {
					return i;
				}
			}
		}
		return null;
	}

	private boolean isNull(Message message) {

		if (message == null || message.getCreationDate() == null || message.getReceiver() == null
				|| message.getSender() == null || message.getBody() == null) {
			return true;
		}
		return false;
	}

}
