package ua.nure.hartseva.SummaryTask4.dao;

import java.sql.SQLException;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.User;

public interface IUsersDao {

	User getUserByEmailAndPassword(String email, String password) throws SQLException;

	List<User> findAllUsersExceptAdmin(int adminId) throws SQLException;

	boolean doesUserExist(String email) throws SQLException;

	int save(User user) throws SQLException;
	
	void changeUserStatus(int userId, String newStatus) throws SQLException;
	
	List<User> getAllUsers() throws SQLException;
}
