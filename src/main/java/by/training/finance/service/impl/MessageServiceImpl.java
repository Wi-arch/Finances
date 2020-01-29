package by.training.finance.service.impl;

import java.util.GregorianCalendar;
import java.util.List;

import by.training.finance.bean.Message;
import by.training.finance.bean.User;
import by.training.finance.dao.MessageDAO;
import by.training.finance.dao.UserDAO;
import by.training.finance.dao.exception.DAOException;
import by.training.finance.dao.factory.DAOFactory;
import by.training.finance.service.MessageService;
import by.training.finance.service.exception.ServiceException;
import by.training.finance.service.exception.messageservice.BlankTextException;
import by.training.finance.service.exception.messageservice.InvalidMessageReceiverException;
import by.training.finance.service.exception.userservice.NotExistingUserException;
import by.training.finance.service.validator.UserValidator;

public class MessageServiceImpl implements MessageService {

	private final DAOFactory DAO_FACTORY = DAOFactory.getInstance();
	private final MessageDAO MESSAGE_DAO = DAO_FACTORY.getMessageDAO();
	private final UserDAO USER_DAO = DAO_FACTORY.getUserDAO();

	@Override
	public void sendMessage(String senderLogin, String receiverLogin, String body) throws ServiceException {

		if (body == null || body.isBlank()) {
			throw new BlankTextException("Cannot send blank message <Status2001>");
		}
		if (!UserValidator.checkLogin(senderLogin)) {
			throw new NotExistingUserException("Sender does not exist <Status1005>");
		}
		if (!UserValidator.checkLogin(receiverLogin)) {
			throw new NotExistingUserException("Receiver does not exist <Status2003>");
		}
		if (senderLogin.equals(receiverLogin)) {
			throw new InvalidMessageReceiverException("Sender matches receiver <Status2002>");
		}
		try {
			User sender = USER_DAO.getUser(senderLogin);
			if (sender == null) {
				throw new NotExistingUserException("Sender does not exist");
			}
			User receiver = USER_DAO.getUser(receiverLogin);
			if (receiver == null) {
				throw new NotExistingUserException("Receiver does not exist <Status2003>");
			}
			Message message = new Message();
			message.setBody(body);
			message.setSender(senderLogin);
			message.setReceiver(receiverLogin);
			message.setCreationDate(new GregorianCalendar().getTime());

			MESSAGE_DAO.saveMessage(message);

		} catch (DAOException e) {
			throw new ServiceException("Cannot send message");
		}
	}

	@Override
	public void deleteMessage(long id) throws ServiceException {

		try {
			MESSAGE_DAO.deleteMessage(id);

		} catch (DAOException e) {
			throw new ServiceException("Cannot delete message", e);
		}
	}

	@Override
	public List<Message> getAllInputMessagesForUser(String login) throws ServiceException {

		if (login == null || !UserValidator.checkLogin(login)) {
			throw new NotExistingUserException("User does not exist");
		}
		try {
			List<Message> inputMessages = MESSAGE_DAO.getMessagesByReceiver(login);
			return inputMessages;

		} catch (DAOException e) {
			throw new ServiceException("Cannot read messages");
		}
	}

	@Override
	public List<Message> getAllOutputMessagesForUser(String login) throws ServiceException {

		if (login == null || !UserValidator.checkLogin(login)) {
			throw new NotExistingUserException("User does not exist");
		}
		try {
			List<Message> outputMessages = MESSAGE_DAO.getMessagesBySender(login);
			return outputMessages;

		} catch (DAOException e) {
			throw new ServiceException("Cannot read messages");
		}
	}
}
