package by.training.finance.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import by.training.finance.bean.User;
import by.training.finance.dao.UserDAO;
import by.training.finance.exception.DAOException;

public class FileUserDAO implements UserDAO {

	private final static String PATH = "user.out";

	@Override
	public synchronized void saveUser(User user) throws DAOException {

		if (isNull(user)) {
			throw new DAOException("User or his values are not initialized");
		}
		List<User> users = getAllUsers();
		Integer index = getIndexOfUser(users, user.getLogin());
		if (index != null) {
			throw new DAOException("Primary key " + user.getLogin() + " already exists");
		}
		users.add(user);
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(users);
		} catch (IOException e) {
			throw new DAOException("Exception while saving user", e);
		}
	}

	@Override
	public synchronized void deleteUser(String login) throws DAOException {

		List<User> users = getAllUsers();
		Integer index = getIndexOfUser(users, login);
		if (index == null) {
			throw new DAOException("Cannot delete non existing user");
		}
		users.remove(index.intValue());
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(users);
		} catch (IOException e) {
			throw new DAOException("Exception while delete user", e);
		}
	}

	@Override
	public synchronized void updateUser(User user) throws DAOException {

		if (isNull(user)) {
			throw new DAOException("User or his values are not initialized");
		}
		List<User> users = getAllUsers();
		Integer index = getIndexOfUser(users, user.getLogin());
		if (index == null) {
			throw new DAOException("Cannot update non existing user");
		}
		users.set(index, user);

		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(users);
		} catch (IOException e) {
			throw new DAOException("Exception while updating user", e);
		}
	}

	@Override
	public synchronized User getUser(String login) throws DAOException {

		List<User> users = getAllUsers();
		Integer integer = getIndexOfUser(users, login);
		return integer == null ? null : users.get(integer.intValue());
	}

	@SuppressWarnings("unchecked")
	@Override
	public synchronized List<User> getAllUsers() throws DAOException {

		if (!new File(PATH).exists()) {
			init();
		}
		List<User> users = null;

		try (ObjectInputStream reader = new ObjectInputStream(new FileInputStream(PATH))) {
			users = (List<User>) reader.readObject();
		} catch (IOException | ClassNotFoundException e) {
			throw new DAOException("Exception while read user", e);
		}
		return users;
	}

	private void init() throws DAOException {

		List<User> users = new ArrayList<User>();
		try (ObjectOutputStream writer = new ObjectOutputStream(new FileOutputStream(PATH))) {
			writer.writeObject(users);
		} catch (IOException e) {
			throw new DAOException("Exception creating file " + PATH, e);
		}
	}

	private Integer getIndexOfUser(List<User> users, String login) {

		if (login != null && users != null) {
			for (int i = 0; i < users.size(); i++) {
				if (login.equals(users.get(i).getLogin())) {
					return i;
				}
			}
		}
		return null;
	}

	private boolean isNull(User user) {

		if (user == null || user.getLogin() == null || user.getPassword() == null) {
			return true;
		}
		return false;
	}

}
