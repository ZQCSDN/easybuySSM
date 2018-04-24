package com.easybuy.service.product;

import com.easybuy.dao.product.ProductMapper;
import com.easybuy.entity.Category;
import com.easybuy.entity.Product;
import com.easybuy.util.MyBatisUtil;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	private List<Category> list=new ArrayList();
	boolean flag=false;
	private Logger log=Logger.getLogger("console");
	private SqlSession sqlSession=null;
	private Object obj=null;
	
	@Override
	public boolean addProduct(Product p) {
		
		
		
		return false;
	}

	@Override
	public boolean deleteProductById(int id) {
		
		
		
		return false;
	}

	@Override
	public boolean updateProduct(Product p) {
		
		
		
		return false;
	}

	@Override
	public List<Product> getListProductByCategoryId(int categoryId) {
		
		
		
		return null;
	}

	@Override
	public Product getProductById(int id) {
		
		
		
		return null;
	}

	@Override
	public int getProductCountByCategoryId(int categoryId) {
		int count=0;
		try {
			sqlSession=MyBatisUtil.createSqlSession();
			count=sqlSession.getMapper(ProductMapper.class).getProductCountByCategoryId(categoryId);
		} catch (Exception e) {
			log.error(e);
		} finally {
			MyBatisUtil.closeSqlSession(sqlSession);
		}
		return count;
	}

	
	
	
	
	
	
	
	
}
