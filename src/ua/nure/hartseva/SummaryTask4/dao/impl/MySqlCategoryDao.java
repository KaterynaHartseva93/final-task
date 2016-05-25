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
import ua.nure.hartseva.SummaryTask4.dao.ICategoryDao;
import ua.nure.hartseva.SummaryTask4.entity.Category;
import ua.nure.hartseva.SummaryTask4.exception.MySqlDaoException;
import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;

public class MySqlCategoryDao extends BaseDao implements ICategoryDao {

	private static final String GET_CATEGORIES = "SELECT * FROM categories";
	private static final String GET_CATEGORY_BY_ID = "SELECT * FROM categories WHERE id=?";
	private static final String GET_CATEGORY_BY_NAME = "SELECT * FROM categories WHERE name=?";
	private static final String CREATE_NEW_CATEGORY = "INSERT INTO categories (name) VALUES (?)";

	public MySqlCategoryDao(IConnectionProvider connectionProvider) {
		super(connectionProvider);
	}

	@Override
	public List<Category> findAllCategories() throws SQLException {
		Connection connection = connectionProvider.getConnection();
		List<Category> categoriesList = new ArrayList<>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(GET_CATEGORIES);
		while (resultSet.next()) {
			String name = resultSet.getString(Attributes.NAME);
			int id = resultSet.getInt(Attributes.ID);
			Category category = new Category(id, name);
			categoriesList.add(category);
		}
		return categoriesList;
	}

	@Override
	public Category getById(int categoryId) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_CATEGORY_BY_ID);
		preparedStatement.setInt(1, categoryId);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			String categoryName = resultSet.getString(Attributes.NAME);
			return new Category(categoryId, categoryName);
		}
		return null;
	}

	@Override
	public Category getByName(String name) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_CATEGORY_BY_NAME);
		preparedStatement.setString(1, name);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			int id = resultSet.getInt(Attributes.ID);
			return new Category(id, name);
		}
		return null;
	}

	@Override
	public int create(String name) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_CATEGORY,
				Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, name);
		if (preparedStatement.executeUpdate() > 0) {
			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
				resultSet.next();
				return resultSet.getInt(1);
			}
		}
		throw new MySqlDaoException("Failed to create new category.");
	}
}
