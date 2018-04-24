package com.easybuy.service.category;


import com.easybuy.dao.category.CategoryMapper;
import com.easybuy.entity.Category;
import com.easybuy.util.MyBatisUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("categoryService")
public class CategoryServiceImpl implements CategoryService {
	
	private List<Category> list=new ArrayList();
	boolean flag=false;
	private Logger log=Logger.getLogger("console");
	private SqlSession sqlSession=null;
	private Object obj=null;
	
	public boolean addCategory(Category c) {
		try {
			sqlSession=MyBatisUtil.createSqlSession();
			int sqlNum=sqlSession.getMapper(CategoryMapper.class).addCategory(c);
			if(sqlNum>0){
				flag=true;
			}
			sqlSession.commit();
		} catch (Exception e) {
			log.error(e);
			sqlSession.rollback();
		} finally {
			MyBatisUtil.closeSqlSession(sqlSession);
		}
		return flag;
	}

	public boolean deleteCategoryById(int id) {
		try {
			sqlSession=MyBatisUtil.createSqlSession();
			int sqlNum=sqlSession.getMapper(CategoryMapper.class).deleteCategoryById(id);
			if(sqlNum>0){
				flag=true;
			}
			sqlSession.commit();
		} catch (Exception e) {
			log.error(e);
			sqlSession.rollback();
		} finally {
			MyBatisUtil.closeSqlSession(sqlSession);
		}
		return flag;
	}

	public boolean updateCategory(Category c) {
		try {
			sqlSession=MyBatisUtil.createSqlSession();
			int sqlNum=sqlSession.getMapper(CategoryMapper.class).updateCategory(c);
			if(sqlNum>0){
				flag=true;
			}
			sqlSession.commit();
		} catch (Exception e) {
			log.error(e);
			sqlSession.rollback();
		} finally {
			MyBatisUtil.closeSqlSession(sqlSession);
		}
		return flag;
	}

	
	public Category getCategoryById(int id) {
		Category c=null;
		try {
			sqlSession=MyBatisUtil.createSqlSession();
			c=sqlSession.getMapper(CategoryMapper.class).getCategoryById(id);
			sqlSession.commit();
		} catch (Exception e) {
			log.error(e);
			sqlSession.rollback();
		} finally {
			MyBatisUtil.closeSqlSession(sqlSession);
		}
		return c;
	}
	
	public List<Category> getListCategory(int startIndex,int pageSize) {
		try {
			sqlSession=MyBatisUtil.createSqlSession();
			list=sqlSession.getMapper(CategoryMapper.class).getListCategory(startIndex,pageSize);
		} catch (Exception e) {
			log.error(e);
		} finally {
			MyBatisUtil.closeSqlSession(sqlSession);
		}
		return list;
	}

	public int getTotalCount() {
		int totalCount=0;
		try {
			sqlSession=MyBatisUtil.createSqlSession();
			totalCount = sqlSession.getMapper(CategoryMapper.class).getTotalCount();
		} catch (SQLException e) {
			log.error(e);
		}
		return totalCount;
	}

	@Override
	public List<Category> getListCategoryByLevel(int level) {
		try {
			sqlSession=MyBatisUtil.createSqlSession();
			list=sqlSession.getMapper(CategoryMapper.class).getListCategoryByLevel(level);
		} catch (Exception e) {
			log.error(e);
		} finally {
			MyBatisUtil.closeSqlSession(sqlSession);
		}
		return list;
	}

	@Override
	public List<Category> getListCategoryByParentId(int parentId) {
		try {
			sqlSession=MyBatisUtil.createSqlSession();
			list=sqlSession.getMapper(CategoryMapper.class).getListCategoryByParentId(parentId);
		} catch (Exception e) {
			log.error(e);
		} finally {
			MyBatisUtil.closeSqlSession(sqlSession);
		}
		return list;
	}
	
	
}
