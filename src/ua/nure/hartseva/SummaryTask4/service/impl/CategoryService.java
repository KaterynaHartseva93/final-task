package ua.nure.hartseva.SummaryTask4.service.impl;

import java.sql.SQLException;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.bean.ITransactedOperation;
import ua.nure.hartseva.SummaryTask4.dao.ICategoryDao;
import ua.nure.hartseva.SummaryTask4.entity.Category;
import ua.nure.hartseva.SummaryTask4.service.BaseService;
import ua.nure.hartseva.SummaryTask4.service.ICategoryService;
import ua.nure.hartseva.SummaryTask4.service.ITransactionProvider;

public class CategoryService extends BaseService implements ICategoryService{

	private final ICategoryDao categoryDao;
	
	public CategoryService(ITransactionProvider transactionProvider, ICategoryDao categoryDao) {
		super(transactionProvider);
		this.categoryDao = categoryDao;
	}
	
	@Override
	public List<Category> getAllCategories() {
		return transactionProvider.execute(new ITransactedOperation<List<Category>>() {
			@Override
			public List<Category> execute() throws SQLException {
				return categoryDao.findAllCategories();
			}
		});
	}

	@Override
	public Category getById(final int categoryId) {
		return transactionProvider.execute(new ITransactedOperation<Category>() {
			@Override
			public Category execute() throws SQLException  {
				return categoryDao.getById(categoryId);
			}
		});
	}

	@Override
	public Category getByName(final String name) {
		return transactionProvider.execute(new ITransactedOperation<Category>() {
			@Override
			public Category execute() throws SQLException  {
				return categoryDao.getByName(name);
			}
		});
	}

	@Override
	public Category create(final String name) {
		return transactionProvider.execute(new ITransactedOperation<Category>() {
			@Override
			public Category execute() throws SQLException {
				return new Category(categoryDao.create(name), name);
			}
		});
	}
}
