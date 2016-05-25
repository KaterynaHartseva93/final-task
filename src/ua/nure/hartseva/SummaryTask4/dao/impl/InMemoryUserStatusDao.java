package ua.nure.hartseva.SummaryTask4.dao.impl;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ua.nure.hartseva.SummaryTask4.dao.IUserStatusDao;
import ua.nure.hartseva.SummaryTask4.dao.IUsersDao;
import ua.nure.hartseva.SummaryTask4.entity.Status;
import ua.nure.hartseva.SummaryTask4.entity.User;
import ua.nure.hartseva.SummaryTask4.exception.MySqlDaoException;

public class InMemoryUserStatusDao implements IUserStatusDao {

	private Map<Integer, Status> usersStatusList;

	public InMemoryUserStatusDao(IUsersDao userDao) {
		usersStatusList = new HashMap<>();
		List<User> users = getUsers(userDao);
		if (!users.isEmpty()) {
			for (User user : users) {
				usersStatusList.put(user.getId(), user.getStatus());
			}
		}
	}

	@Override
	public boolean isBlocked(int userId) {
		if (usersStatusList.get(userId).equals(Status.BLOCKED)) {
			return true;
		}
		return false;
	}

	@Override
	public void changeUserStatus(int userId, Status newStatus) {
		usersStatusList.put(userId, newStatus);
	}
	
	private List<User> getUsers(IUsersDao userDao) {
		try {
			return userDao.getAllUsers();
		} catch (SQLException e) {
			throw new MySqlDaoException("Failed to get all users. Reason: " + e.getMessage(), e);
		}
	}
}
