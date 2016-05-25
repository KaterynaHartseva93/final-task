package ua.nure.hartseva.SummaryTask4.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.Attributes;
import ua.nure.hartseva.SummaryTask4.dao.BaseDao;
import ua.nure.hartseva.SummaryTask4.dao.IUsersDao;
import ua.nure.hartseva.SummaryTask4.entity.Gender;
import ua.nure.hartseva.SummaryTask4.entity.Role;
import ua.nure.hartseva.SummaryTask4.entity.Status;
import ua.nure.hartseva.SummaryTask4.entity.User;
import ua.nure.hartseva.SummaryTask4.exception.MySqlDaoException;
import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;

public class MySqlUsersDao extends BaseDao implements IUsersDao {

	private static final String GET_USER_BY_EMAIL_AND_PASSWORD_QUERY = "SELECT * FROM users WHERE email=? AND password=?";
	private static final String GET_USER_BY_EMAIL_QUERY = "SELECT * FROM users WHERE email=?";
	private static final String GET_USERS_EXCEPT_ADMIN = "SELECT * FROM users WHERE id!=?";
	private static final String GET_ALL_USERS = "SELECT * FROM users";
	private static final String UPDATE_FIELD_IN_THE_TABLE = "UPDATE users SET status=? WHERE id=?";
	private static final String SAVE_USER_QUERY = "INSERT INTO users (first_name, last_name, email, gender, status, password, role) VALUES (?, ?, ?, ?, ?, ?, ?)";

	public MySqlUsersDao(IConnectionProvider connectionProvider) {
		super(connectionProvider);
	}

	@Override
	public User getUserByEmailAndPassword(String email, String password) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_AND_PASSWORD_QUERY);
		preparedStatement.setString(1, email.toLowerCase());
		preparedStatement.setString(2, password);

		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet == null || !resultSet.next()) {
			return null;
		}

		return getUserFromResultSet(resultSet);
	}

	@Override
	public List<User> findAllUsersExceptAdmin(int adminId) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		List<User> usersList = new ArrayList<>();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_USERS_EXCEPT_ADMIN);
		preparedStatement.setInt(1, adminId);
		ResultSet resultSet = preparedStatement.executeQuery();
		while (resultSet.next()) {
			User user = getUserFromResultSet(resultSet);
			usersList.add(user);
		}
		return usersList;
	}

	@Override
	public boolean doesUserExist(String email) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL_QUERY);
		preparedStatement.setString(1, email.toLowerCase());

		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet == null || !resultSet.next()) {
			return false;
		}
		return true;
	}

	@Override
	public int save(User user) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		int n = 1;
		PreparedStatement preparedStatement = connection.prepareStatement(SAVE_USER_QUERY,
				Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(n++, user.getFirstName());
		preparedStatement.setString(n++, user.getLastName());
		preparedStatement.setString(n++, user.getEmail().toLowerCase());
		preparedStatement.setString(n++, user.getGender().name());
		preparedStatement.setString(n++, user.getStatus().name());
		preparedStatement.setString(n++, user.getPassword());
		preparedStatement.setString(n++, user.getRole().name());

		if (preparedStatement.executeUpdate() > 0) {
			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
				resultSet.next();
				return resultSet.getInt(1);
			}
		}
		throw new MySqlDaoException("Failed to save user to database.");
	}

	@Override
	public void changeUserStatus(int userId, String newStatus) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FIELD_IN_THE_TABLE);
		preparedStatement.setString(1, newStatus);
		preparedStatement.setInt(2, userId);
		preparedStatement.executeUpdate();
	}

	@Override
	public List<User> getAllUsers() throws SQLException {
		Connection connection = connectionProvider.getConnection();
		List<User> usersList = new ArrayList<>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(GET_ALL_USERS);
		while (resultSet.next()) {
			User user = getUserFromResultSet(resultSet);
			usersList.add(user);
		}
		return usersList;
	}

	private User getUserFromResultSet(ResultSet resultSet) throws SQLException {
		int id = resultSet.getInt(Attributes.ID);
		Role role = Role.valueOf(resultSet.getString(Attributes.ROLE));
		Gender gender = Gender.valueOf(resultSet.getString(Attributes.GENDER));
		Status status = Status.valueOf(resultSet.getString(Attributes.STATUS));

		String firstName = resultSet.getString(Attributes.FIRST_NAME);
		String lastName = resultSet.getString(Attributes.LAST_NAME);
		String email = resultSet.getString(Attributes.EMAIL);

		User user = new User();
		user.setId(id);
		user.setEmail(email);
		user.setRole(role);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setGender(gender);
		user.setStatus(status);
		return user;
	}
}
