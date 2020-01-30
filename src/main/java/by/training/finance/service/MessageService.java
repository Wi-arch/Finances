package by.training.finance.service;

import java.util.List;

import by.training.finance.bean.Message;
import by.training.finance.exception.ServiceException;

public interface MessageService {

	public void sendMessage(String senderLogin, String receiverLogin, String body) throws ServiceException;

	public void deleteMessage(long id) throws ServiceException;

	public List<Message> getAllInputMessagesForUser(String login) throws ServiceException;
	
	public List<Message> getAllOutputMessagesForUser(String login) throws ServiceException;

}
