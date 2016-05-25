package ua.nure.hartseva.SummaryTask4.service;

import ua.nure.hartseva.SummaryTask4.bean.ITransactedOperation;

public interface ITransactionProvider {
	
	<T> T execute(ITransactedOperation<T> operation);
}
