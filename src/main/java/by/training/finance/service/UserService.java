package by.training.finance.service;

import by.training.finance.service.exception.ServiceException;

public interface UserService {

	public void registration(String login, String password, String passwordConfirm) throws ServiceException;

	public void logIn(String login, String password) throws ServiceException;

	public void logOut(String login) throws ServiceException;

	public void updatePassword(String login, String password, String passwordConfirm) throws ServiceException;

	public void deleteUser(String login, String password) throws ServiceException;

}
