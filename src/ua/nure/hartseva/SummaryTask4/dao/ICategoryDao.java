package ua.nure.hartseva.SummaryTask4.dao;

import java.sql.SQLException;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.Category;

public interface ICategoryDao {

	List<Category> findAllCategories() throws SQLException; 
	
	Category getById(int categoryId) throws SQLException;

	Category getByName(String name) throws SQLException;

	int create(String name) throws SQLException;
}
