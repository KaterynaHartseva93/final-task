package ua.nure.hartseva.SummaryTask4.service.impl;

import java.sql.SQLException;
import java.util.List;

import ua.nure.hartseva.SummaryTask4.bean.ITransactedOperation;
import ua.nure.hartseva.SummaryTask4.dao.IBrandDao;
import ua.nure.hartseva.SummaryTask4.entity.Brand;
import ua.nure.hartseva.SummaryTask4.service.BaseService;
import ua.nure.hartseva.SummaryTask4.service.IBrandService;
import ua.nure.hartseva.SummaryTask4.service.ITransactionProvider;

public class BrandService extends BaseService implements IBrandService {
	
	private final IBrandDao brandDao;
	
	public BrandService(ITransactionProvider transactionProvider, IBrandDao brandDao) {
		super(transactionProvider);
		this.brandDao = brandDao;
	}

	@Override
	public List<Brand> getAllBrands() {
		return transactionProvider.execute(new ITransactedOperation<List<Brand>>() {
			@Override
			public List<Brand> execute() throws SQLException  {
				return brandDao.findAllBrands();
			}
		});
	}

	@Override
	public Brand getById(final int brandId) {
		return transactionProvider.execute(new ITransactedOperation<Brand>() {
			@Override
			public Brand execute() throws SQLException  {
				return brandDao.getById(brandId);
			}
		});
	}

	@Override
	public Brand getByName(final String name) {
		return transactionProvider.execute(new ITransactedOperation<Brand>() {
			@Override
			public Brand execute() throws SQLException  {
				return brandDao.getByName(name);
			}
		});
	}

	@Override
	public Brand create(final String name) {
		return transactionProvider.execute(new ITransactedOperation<Brand>() {
			@Override
			public Brand execute() throws SQLException {
				return new Brand(brandDao.create(name), name);
			}
		});
	}
}
