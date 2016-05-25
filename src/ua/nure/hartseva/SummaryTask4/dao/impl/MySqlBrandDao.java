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
import ua.nure.hartseva.SummaryTask4.dao.IBrandDao;
import ua.nure.hartseva.SummaryTask4.entity.Brand;
import ua.nure.hartseva.SummaryTask4.exception.MySqlDaoException;
import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;

public class MySqlBrandDao extends BaseDao implements IBrandDao {
	
	private static final String GET_BRANDS = "SELECT * FROM brands";
	private static final String GET_BRAND_BY_ID = "SELECT * FROM brands WHERE id=?";
	private static final String GET_BRAND_BY_NAME = "SELECT * FROM brands WHERE name=?";
	private static final String CREATE_NEW_BRAND = "INSERT INTO brands (name) VALUES (?)";

	public MySqlBrandDao(IConnectionProvider connectionProvider) {
		super(connectionProvider);
	}
	
	@Override
	public List<Brand> findAllBrands() throws SQLException {
		Connection connection = connectionProvider.getConnection();
		List<Brand> brandsList = new ArrayList<>();
		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery(GET_BRANDS);
		while (resultSet.next()) {
			String name = resultSet.getString(Attributes.NAME);
			int id = resultSet.getInt(Attributes.ID);
			Brand brand = new Brand(id, name);
			brandsList.add(brand);
		}
		return brandsList;
	}

	@Override
	public Brand getById(int brandId) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_BRAND_BY_ID);
		preparedStatement.setInt(1, brandId);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			String brandName = resultSet.getString(Attributes.NAME);
			return new Brand(brandId, brandName);
		}
		return null;
	}

	@Override
	public Brand getByName(String name) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(GET_BRAND_BY_NAME);
		preparedStatement.setString(1, name);
		ResultSet resultSet = preparedStatement.executeQuery();
		if (resultSet.next()) {
			int id = resultSet.getInt(Attributes.ID);
			return new Brand(id, name);
		}
		return null;
	}

	@Override
	public int create(String name) throws SQLException {
		Connection connection = connectionProvider.getConnection();
		PreparedStatement preparedStatement = connection.prepareStatement(CREATE_NEW_BRAND, Statement.RETURN_GENERATED_KEYS);
		preparedStatement.setString(1, name);
		if (preparedStatement.executeUpdate() > 0) {
			try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
				resultSet.next();
				return resultSet.getInt(1);
			}
		}
		throw new MySqlDaoException("Failed to create new brand.");
	}
}
