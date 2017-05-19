package org.sobakaisti.core.service;

import java.util.List;

import org.sobakaisti.core.dao.CategoryDao;
import org.sobakaisti.core.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	@Override
	public List<Category> findAllCategories() {
		return categoryDao.findAll();
	}

}
