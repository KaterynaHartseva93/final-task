package ua.nure.hartseva.SummaryTask4.dao;

import java.sql.SQLException;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.Brand;

public interface IBrandDao {

	List<Brand> findAllBrands() throws SQLException;
	
	Brand getById(int brandId) throws SQLException;
	
	Brand getByName(String name) throws SQLException;
	
	int create(String name) throws SQLException;
}
