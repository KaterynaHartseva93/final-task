package ua.nure.hartseva.SummaryTask4.bean;

import java.sql.SQLException;

public interface ITransactedOperation<T> {

	T execute() throws SQLException;
}
