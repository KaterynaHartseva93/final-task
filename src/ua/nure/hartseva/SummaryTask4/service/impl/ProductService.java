package ua.nure.hartseva.SummaryTask4.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import ua.nure.hartseva.SummaryTask4.bean.ITransactedOperation;
import ua.nure.hartseva.SummaryTask4.bean.ProductSearchCriteria;
import ua.nure.hartseva.SummaryTask4.bean.ProductUpdateCriteria;
import ua.nure.hartseva.SummaryTask4.dao.IBrandDao;
import ua.nure.hartseva.SummaryTask4.dao.ICategoryDao;
import ua.nure.hartseva.SummaryTask4.dao.IProductDao;
import ua.nure.hartseva.SummaryTask4.dao.IProductInfoDao;
import ua.nure.hartseva.SummaryTask4.dao.IProductSizeDao;
import ua.nure.hartseva.SummaryTask4.entity.Brand;
import ua.nure.hartseva.SummaryTask4.entity.Category;
import ua.nure.hartseva.SummaryTask4.entity.Size;
import ua.nure.hartseva.SummaryTask4.entity.product.Product;
import ua.nure.hartseva.SummaryTask4.entity.product.ProductSize;
import ua.nure.hartseva.SummaryTask4.service.BaseService;
import ua.nure.hartseva.SummaryTask4.service.IProductService;
import ua.nure.hartseva.SummaryTask4.service.ITransactionProvider;

public class ProductService extends BaseService implements IProductService {

	private final IProductDao productDao;
	private final IProductSizeDao productSizeDao;
	private final IBrandDao brandDao;
	private final ICategoryDao categoryDao;
	private final IProductInfoDao productInfoDao;

	public ProductService(ITransactionProvider transactionProvider, IProductDao productDao,
			IProductSizeDao productSizeDao, IProductInfoDao productInfoDao, IBrandDao brandDao,
			ICategoryDao categoryDao) {
		super(transactionProvider);
		this.productDao = productDao;
		this.productSizeDao = productSizeDao;
		this.brandDao = brandDao;
		this.categoryDao = categoryDao;
		this.productInfoDao = productInfoDao;
	}

	@Override
	public List<Product> findByCriteria(final ProductSearchCriteria criteria) {
		return transactionProvider.execute(new ITransactedOperation<List<Product>>() {
			@Override
			public List<Product> execute() throws SQLException {
				List<Product> products = productDao.getByCriteria(criteria);
				return fillWithSizes(products);
			}
		});
	}

	@Override
	public List<Product> findNewest(final int count) {
		return transactionProvider.execute(new ITransactedOperation<List<Product>>() {
			@Override
			public List<Product> execute() throws SQLException {
				List<Product> products = productDao.getNewest(count);
				return fillWithSizes(products);
			}
		});
	}

	@Override
	public List<Product> findPopular(final int count) {
		return transactionProvider.execute(new ITransactedOperation<List<Product>>() {
			@Override
			public List<Product> execute() throws SQLException {
				List<Product> products = productDao.getPopular(count);
				return fillWithSizes(products);
			}
		});
	}

	@Override
	public Product getProductById(final int id) {
		return transactionProvider.execute(new ITransactedOperation<Product>() {
			@Override
			public Product execute() throws SQLException {
				Product product = productDao.getProductById(id);
				if (product == null) {
					return null;
				}
				List<Product> products = Arrays.asList(product);
				Product productWithSizes = fillWithSizes(products).get(0);
				Brand brand = brandDao.getById(productWithSizes.getBrand().getId());
				Category category = categoryDao.getById(productWithSizes.getCategory().getId());
				productWithSizes.getBrand().setName(brand.getName());
				productWithSizes.getCategory().setName(category.getName());
				return productWithSizes;
			}
		});
	}

	@Override
	public void update(final Product product, final ProductUpdateCriteria criteria) {
		transactionProvider.execute(new ITransactedOperation<Void>() {
			@Override
			public Void execute() throws SQLException {
				List<Size> sizesToDelete = getSizesToDelete(product.getAvailableSizes(), criteria.getSizesList());
				List<Size> sizesToAdd = getSizesToAdd(product.getAvailableSizes(), criteria.getSizesList());
				if (!sizesToAdd.isEmpty()) {
					productSizeDao.addSizes(product.getId(), sizesToAdd);
				}
				if (!sizesToDelete.isEmpty()) {
					productSizeDao.deleteSizes(product.getId(), sizesToDelete);
				}
				productDao.update(product, criteria);
				return null;
			}
		});
	}

	@Override
	public void create(final Product product) {
		// Perform operation in transaction
		transactionProvider.execute(new ITransactedOperation<Void>() {
			@Override
			public Void execute() throws SQLException {
				int brandId = getBrandId(product.getBrand().getName());
				int categoryId = getCategoryId(product.getCategory().getName());

				Brand brand = product.getBrand();
				Category category = product.getCategory();

				brand.setId(brandId);
				category.setId(categoryId);

				int productId = productDao.create(product);

				product.getInfo().setProductId(productId);
				productInfoDao.create(product.getInfo());
				productSizeDao.addSizes(productId, new ArrayList<>(product.getAvailableSizes()));
				return null;
			}
		});
	}

	@Override
	public void delete(final int id) {
		transactionProvider.execute(new ITransactedOperation<Void>() {
			@Override
			public Void execute() throws SQLException {
				productDao.delete(id);
				return null;
			}
		});
	}

	@Override
	public void updateSellCount(final int productId) {
		transactionProvider.execute(new ITransactedOperation<Void>() {
			@Override
			public Void execute() throws SQLException {
				productInfoDao.updateSellCount(productId);
				return null;
			}
		});
	}

	@Override
	public boolean doesProductExist(final int productId, final String size) {
		return transactionProvider.execute(new ITransactedOperation<Boolean>() {
			@Override
			public Boolean execute() throws SQLException {
				return productDao.doesProductExist(productId, size);
			}
		});
	}

	@Override
	public int getProductsCount(final ProductSearchCriteria criteria) {
		return transactionProvider.execute(new ITransactedOperation<Integer>() {
			@Override
			public Integer execute() throws SQLException {
				return productDao.getCountByCriteria(criteria);
			}
		});
	}

	private int getBrandId(String brandName) throws SQLException {
		Brand brand = brandDao.getByName(brandName);
		if (brand == null) {
			return brandDao.create(brandName);
		}
		return brand.getId();
	}

	private int getCategoryId(String categoryName) throws SQLException {
		Category category = categoryDao.getByName(categoryName);
		if (category == null) {
			return categoryDao.create(categoryName);
		}
		return category.getId();
	}

	private List<Product> fillWithSizes(List<Product> products) throws SQLException {
		if (products == null || products.isEmpty()) {
			return products;
		}
		List<Product> productsWithSizes = new ArrayList<>(products);
		List<Integer> productIds = getProductIds(productsWithSizes);

		List<ProductSize> productSizes = productSizeDao.findProductSizes(productIds);
		Map<Integer, Set<Size>> productSizesMap = getProductSizesMap(productSizes);

		for (Product product : productsWithSizes) {
			int productId = product.getId();
			Set<Size> sizesSet = productSizesMap.get(productId);
			product.setAvailableSizes(sizesSet);
		}
		return productsWithSizes;
	}

	private List<Integer> getProductIds(List<Product> products) {
		List<Integer> productIds = new ArrayList<>();
		for (Product product : products) {
			productIds.add(product.getId());
		}
		return productIds;
	}

	private Map<Integer, Set<Size>> getProductSizesMap(List<ProductSize> productSizes) {
		Map<Integer, Set<Size>> map = new HashMap<Integer, Set<Size>>();
		for (ProductSize productSize : productSizes) {
			Set<Size> sizesSet = map.get(productSize.getProductId());
			if (sizesSet == null) {
				sizesSet = new TreeSet<>();
				map.put(productSize.getProductId(), sizesSet);
			}
			sizesSet.add(productSize.getSize());
		}
		return map;
	}

	private List<Size> getSizesToAdd(Set<Size> availableSizes, List<Size> sizesList) {
		Collection<Size> notEmptyAvailableSizes = notEmpty(availableSizes);
		Collection<Size> notEmptySizesList = notEmpty(sizesList);
		notEmptySizesList.removeAll(notEmptyAvailableSizes);
		return new ArrayList<>(notEmptySizesList);
	}

	private List<Size> getSizesToDelete(Set<Size> availableSizes, List<Size> sizesList) {
		Collection<Size> notEmptyAvailableSizes = notEmpty(availableSizes);
		Collection<Size> notEmptySizesList = notEmpty(sizesList);
		notEmptyAvailableSizes.removeAll(notEmptySizesList);
		return new ArrayList<>(notEmptyAvailableSizes);
	}

	private Collection<Size> notEmpty(Collection<Size> sizes) {
		if (sizes == null) {
			return new ArrayList<>();
		}
		return new ArrayList<>(sizes);
	}
}
