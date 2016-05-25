package ua.nure.hartseva.SummaryTask4.dao;

import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;

public abstract class BaseDao {

	protected final IConnectionProvider connectionProvider;
	
	public BaseDao(IConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}
}
