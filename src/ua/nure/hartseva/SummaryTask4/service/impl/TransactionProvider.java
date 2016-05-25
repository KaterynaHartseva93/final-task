package ua.nure.hartseva.SummaryTask4.service.impl;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.bean.ITransactedOperation;
import ua.nure.hartseva.SummaryTask4.exception.MySqlDaoException;
import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;
import ua.nure.hartseva.SummaryTask4.service.ITransactionProvider;

public class TransactionProvider implements ITransactionProvider {

	private static final Logger LOG = Logger.getLogger(TransactionProvider.class);
	
	private final IConnectionProvider connectionProvider;
	
	public TransactionProvider(IConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}
	
	@Override
	public <T> T execute(ITransactedOperation<T> operation) {
		try {
			T result = operation.execute();
			connectionProvider.commit();
			return result;
		} catch (SQLException e) {
			LOG.error("Failed to perform action in transaction. Reason: " + e.getMessage());
			connectionProvider.rollback();
			throw new MySqlDaoException(e.getMessage(), e);
		} finally {
			connectionProvider.close();
		}
	}
}
