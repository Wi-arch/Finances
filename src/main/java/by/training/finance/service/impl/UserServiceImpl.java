package by.training.finance.service.impl;

import java.util.List;

import by.training.finance.bean.Card;
import by.training.finance.bean.User;
import by.training.finance.dao.UserDAO;
import by.training.finance.exception.DAOException;
import by.training.finance.exception.ExistsLoginException;
import by.training.finance.exception.IncorrectLoginException;
import by.training.finance.exception.IncorrectPasswordException;
import by.training.finance.exception.InvalidLoginException;
import by.training.finance.exception.InvalidPasswordException;
import by.training.finance.exception.NotExistingUserException;
import by.training.finance.exception.ServiceException;
import by.training.finance.exception.WrongConfirmPasswordException;
import by.training.finance.factory.DAOFactory;
import by.training.finance.factory.ServiceFactory;
import by.training.finance.service.CardService;
import by.training.finance.service.UserService;
import by.training.finance.validator.UserValidator;

public class UserServiceImpl implements UserService {

	private final DAOFactory DAO_FACTORY = DAOFactory.getInstance();
	private final UserDAO USER_DAO = DAO_FACTORY.getUserDAO();

	@Override
	public void registration(String login, String password, String passwordConfirm) throws ServiceException {

		if (!UserValidator.checkLogin(login)) {
			throw new InvalidLoginException("Invalid login <Status1001>");
		}
		if (!UserValidator.checkPassword(password)) {
			throw new InvalidPasswordException("Invalid password <Status1002>");
		}
		if (!password.equals(passwordConfirm)) {
			throw new WrongConfirmPasswordException("Passwords do not match <Status1004>");
		}
		try {
			User tempUser = USER_DAO.getUser(login);
			if (tempUser != null) {
				throw new ExistsLoginException("User with login " + login + " already exists <Status1003>");
			}
			User user = new User();
			user.setLogin(login);
			String encryptedPassword = encryptPassword(password);
			user.setPassword(encryptedPassword);
			USER_DAO.saveUser(user);

		} catch (DAOException e) {
			throw new ServiceException("Cannot save User", e);
		}

	}

	@Override
	public void logIn(String login, String password) throws ServiceException {

		if (!UserValidator.checkLogin(login)) {
			throw new IncorrectLoginException("Incorrect login <Status1005>");
		}
		if (!UserValidator.checkPassword(password)) {
			throw new IncorrectPasswordException("Incorrect password <Status1006>");
		}
		try {
			User user = USER_DAO.getUser(login);
			if (user == null) {
				throw new IncorrectLoginException("Incorrect login <Status1005>");
			}
			if (!encryptPassword(password).equals(user.getPassword())) {
				throw new IncorrectPasswordException("Incorrect password <Status1006>");
			}
			user.setOnline(true);
			USER_DAO.updateUser(user);

		} catch (DAOException e) {
			throw new ServiceException("Cannot sign in", e);
		}
	}

	@Override
	public void logOut(String login) throws ServiceException {

		if (!UserValidator.checkLogin(login)) {
			throw new NotExistingUserException("User does not exist <Status1005>");
		}
		try {
			User user = USER_DAO.getUser(login);
			if (user == null) {
				throw new NotExistingUserException("User does not exist <Status1005>");
			}
			user.setOnline(false);
			USER_DAO.updateUser(user);

		} catch (DAOException e) {
			throw new ServiceException("Cannot log out", e);
		}
	}

	@Override
	public void updatePassword(String login, String password, String passwordConfirm) throws ServiceException {

		if (!UserValidator.checkLogin(login)) {
			throw new NotExistingUserException("User does not exist <Status1005>");
		}
		if (!UserValidator.checkPassword(password)) {
			throw new InvalidPasswordException("Invalid password <Status1002>");
		}
		if (!password.equals(passwordConfirm)) {
			throw new WrongConfirmPasswordException("Passwords do not match <Status1004>");
		}
		try {
			User user = USER_DAO.getUser(login);
			if (user == null) {
				throw new NotExistingUserException("User does not exist <Status1005>");
			}
			if (user.getPassword().equals(encryptPassword(password))) {
				throw new ServiceException("New password matches old password <Status1007>");
			}
			user.setPassword(encryptPassword(password));
			USER_DAO.updateUser(user);

		} catch (DAOException e) {
			throw new ServiceException("Cannot update password", e);
		}

	}

	@Override
	public void deleteUser(String login, String password) throws ServiceException {

		if (!UserValidator.checkLogin(login)) {
			throw new NotExistingUserException("User does not exist <Status1005>");
		}
		if (!UserValidator.checkPassword(password)) {
			throw new IncorrectPasswordException("Incorrect password <Status1006>");
		}

		try {
			User user = USER_DAO.getUser(login);
			if (user == null) {
				throw new NotExistingUserException("User does not exist <Status1005>");
			}
			if (!user.getPassword().equals(encryptPassword(password))) {
				throw new IncorrectPasswordException("Incorrect password <Status1006>");
			}
			USER_DAO.deleteUser(login);

			ServiceFactory serviceFactory = ServiceFactory.getInstance();
			CardService cardService = serviceFactory.getCardService();
			List<Card> cards = cardService.getAllCardsByHolder(login);
			for (Card card : cards) {
				cardService.deleteCard(card.getNumber());
			}

		} catch (DAOException e) {
			throw new ServiceException("Cannot delete User", e);
		}
	}

	private String encryptPassword(String s) {

		StringBuilder sb = new StringBuilder(s).reverse();
		StringBuilder result = new StringBuilder();
		for (int i = 0; i < sb.length(); i++) {
			char ch = sb.charAt(i);
			result.append(ch += 100 + i);
		}
		return result.toString();
	}

}
