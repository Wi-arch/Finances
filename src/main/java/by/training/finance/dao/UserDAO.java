package by.training.finance.dao;

import java.util.List;

import by.training.finance.bean.User;
import by.training.finance.exception.DAOException;

public interface UserDAO {

	public void saveUser(User user) throws DAOException;

	public void deleteUser(String login) throws DAOException;

	public void updateUser(User user) throws DAOException;

	public User getUser(String login) throws DAOException;

	public List<User> getAllUsers() throws DAOException;
}
