package com.easybuy.service.category;

import com.easybuy.entity.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {

	/**
	 * 增加
	 * @param 
	 * @return
	 */
	public boolean addCategory(Category c);

	/**
	 * 删除
	 * @param 
	 * @return
	 */
	public boolean deleteCategoryById(int id);

	/**
	 * 修改
	 * @param 
	 * @return
	 */
	public boolean updateCategory(Category c);
	
	/**
	 * 查询
	 * @return
	 */
	public List<Category> getListCategory(int startIndex,int pageSize);

	/**
	 * 根据id查询
	 * @param 
	 * @return
	 */
	public Category getCategoryById(int id);

	public int getTotalCount()throws SQLException;

	public List<Category> getListCategoryByLevel(int level);
	
	public List<Category> getListCategoryByParentId(int parentId);
	
	
}
