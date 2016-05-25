package ua.nure.hartseva.SummaryTask4.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import ua.nure.hartseva.SummaryTask4.service.IConnectionProvider;

public class ConnectionProvider implements IConnectionProvider {

	private static final String NAMED_OBJECT = "java:comp/env/jdbc/store";
	private static final Logger LOG = Logger.getLogger(ConnectionProvider.class);

	private Map<Long, Connection> connectionsMap = new ConcurrentHashMap<>();

	private final DataSource dataSource;

	{
		try {
			Context initContext = new InitialContext();
			dataSource = (DataSource) initContext.lookup(NAMED_OBJECT);
		} catch (NamingException ex) {
			LOG.error("Failed to initialize data source. ", ex);
			throw new IllegalStateException("Failed to obtain connection from pool.");
		}
	}

	@Override
	public Connection getConnection() {
		long key = getThreadId();
		Connection connection = connectionsMap.get(key);
		if (connection == null) {
			connection = getConnectionFromDataSource();
			connectionsMap.put(key, connection);
			LOG.info("Created new connection for thread with id=" + key);
		} else {
			LOG.info("Use already created connection for thread with id=" + key);
		}
		return connection;
	}

	@Override
	public void commit() {
		long key = getThreadId();
		try {
			LOG.info("Commit for connection in thread with id=" + key);
			connectionsMap.get(key).commit();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}

	@Override
	public void rollback() {
		long key = getThreadId();
		try {
			LOG.warn("Rollback for connection in thread with id=" + key);
			connectionsMap.get(key).rollback();
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}

	@Override
	public void close() {
		long key = getThreadId();
		try {
			connectionsMap.get(key).close();
			// REMOVE FROM MAP WHEN CLOSING
			connectionsMap.remove(key);
			LOG.info("Closed and removed connection for thread with id=" + key);
		} catch (SQLException e) {
			LOG.error(e.getMessage());
		}
	}

	private Connection getConnectionFromDataSource() {
		try {
			return dataSource.getConnection();
		} catch (SQLException ex) {
			LOG.error("Cannot obtain a connection from the pool. ", ex);
			throw new IllegalStateException("Failed to obtain connection from pool.");
		}
	}

	private long getThreadId() {
		return Thread.currentThread().getId();
	}
}
