package ua.nure.hartseva.SummaryTask4.service;

import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.Brand;

public interface IBrandService {

	List<Brand> getAllBrands();
	
	Brand getById(int brandId);
	
	Brand getByName(String name);
	
	Brand create(String name);
}
