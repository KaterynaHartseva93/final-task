package ua.nure.hartseva.SummaryTask4.service;

import java.sql.Connection;

public interface IConnectionProvider {

	Connection getConnection();
	
	void commit();
	
	void rollback();
	
	void close();
}
