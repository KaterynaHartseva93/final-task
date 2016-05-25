package ua.nure.hartseva.SummaryTask4.service;

import java.util.List;

import ua.nure.hartseva.SummaryTask4.entity.Category;

public interface ICategoryService {

	List<Category> getAllCategories();
	
	Category getById(int categoryId);

	Category create(String name);

	Category getByName(String name);
}
