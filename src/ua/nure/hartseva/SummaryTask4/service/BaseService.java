package ua.nure.hartseva.SummaryTask4.service;

public abstract class BaseService {

	protected final ITransactionProvider transactionProvider;
	
	public BaseService(ITransactionProvider transactionProvider) {
		this.transactionProvider = transactionProvider;
	}
}
