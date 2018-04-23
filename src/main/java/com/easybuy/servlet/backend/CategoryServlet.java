package com.easybuy.servlet.backend;

import com.easybuy.entity.Category;
import com.easybuy.service.category.CategoryService;
import com.easybuy.service.category.CategoryServiceImpl;
import com.easybuy.service.product.ProductService;
import com.easybuy.service.product.ProductServiceImpl;
import com.easybuy.servlet.web.AbstractServlet;
import com.easybuy.util.Constants;
import com.easybuy.util.Page;
import com.easybuy.util.ReturnResult;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/backend/categoryServlet")
public class CategoryServlet extends AbstractServlet {

	private CategoryService categoryService;
	private ProductService	productService;
	
	public void init() throws ServletException {
		this.categoryService=new CategoryServiceImpl();
		this.productService=new ProductServiceImpl();
	}
	
	public Class getServletClass() {
		return CategoryServlet.class;
	}

	public String getListCategory(HttpServletRequest request,
				HttpServletResponse response) throws Exception{
	
		//分页功能
		int totalCount=categoryService.getTotalCount();
		//System.out.println("totalCount:"+totalCount);
		//创建Page对象的时候，totalCount\totalPageCount都初始化成功
		Page page=new Page(totalCount);
		String currentPageNoStr = request.getParameter("currentPageNo");
		//System.out.println(page.getCurrentPageNo());
		String pageSizeStr=request.getParameter("pageSize");
		page.setUrl("backend/categoryServlet?action=getListCategory");
		if(pageSizeStr!=null){
			int pageSize=Integer.parseInt(pageSizeStr);
			page.setPageSize(pageSize);
		}
		if(currentPageNoStr!=null){
			int currentPageNo=Integer.parseInt(currentPageNoStr);
			page.setCurrentPageNo(currentPageNo);
		}
		request.setAttribute("page", page);
		
		//获取数据
		List<Category> list=categoryService.getListCategory(
				page.getStartIndex(),page.getPageSize());
		request.setAttribute("categoryList", list);
		
		//取到数据后，转发到categoryList.jsp页面，这个页面在/backend下
		return "/backend/categoryList";
	}
	
	//点击添加产品时执行
	public String addCategory(HttpServletRequest request,HttpServletResponse response){
		int level=1;
		List<Category> list=categoryService.getListCategoryByLevel(level);
		Category c=new Category();
		request.setAttribute("categoryList1",list);
        request.setAttribute("category",c);
		return "/backend/addCategory";
	}
	
	//点击确定添加产品时执行
	public ReturnResult addProductCategory(HttpServletRequest request,HttpServletResponse response){
		ReturnResult result=new ReturnResult();
		
		int parentId=0;
		String name=request.getParameter("name");
		String level=request.getParameter("level");
        String categoryLevel1=request.getParameter("categoryLevel1");
        String categoryLevel2=request.getParameter("categoryLevel2");
        
        if(level.equals("1")){
            parentId =0;
        }else if(level.equals("2")){
            parentId =Integer.parseInt(categoryLevel1);
        }else{
            parentId =Integer.parseInt(categoryLevel2);
        }
        
        Category c=new Category();
        c.setLevel(Integer.parseInt(level));
        c.setName(name);
        c.setParentId(parentId);
        
        categoryService.addCategory(c);
        result.returnSuccess();
		return result;
	}
	
	//添加产品时，需要根据一级类别获取二级类别的列表
	public ReturnResult getListCategoryByParentId(HttpServletRequest request,HttpServletResponse response){
		ReturnResult result=new ReturnResult();
		String action=request.getParameter("action");
		String parentIdStr=request.getParameter("parentId");
		List<Category> list=null;
		if(parentIdStr!=null){
			list=categoryService.getListCategoryByParentId(Integer.parseInt(parentIdStr));			
		}
		result.setStatus(Constants.ReturnResult.SUCCESS);
		result.setData(list);
		return result;
	}
	
	public ReturnResult deleteCategory(HttpServletRequest request,HttpServletResponse response){
		ReturnResult result=new ReturnResult();
		String id=request.getParameter("id");
		String level=request.getParameter("level");
		
		List<Category> list=null;
		if(id!=null){
			list=categoryService.getListCategoryByParentId(Integer.parseInt(id));			
		}
		if(list.size()>0){
			return result.returnFail("已经存在子分类，不能删除");
		}		
		int count=productService.getProductCountByCategoryId(Integer.parseInt(id));
		if(count>0){
			return result.returnFail("已经存在商品，不能删除");
		}
		
		categoryService.deleteCategoryById(Integer.parseInt(id));
		result.returnSuccess();
		return result;
	}
	
	//点击瞬间，出现修改框
	public String updateCategory(HttpServletRequest request,HttpServletResponse response){
		
		String id=request.getParameter("id");
		Category c=null;
		if(id!=null){
			c=categoryService.getCategoryById(Integer.parseInt(id));
		}
		request.setAttribute("category", c);		
		
		List<Category> list1=null;
        List<Category> list2=null;
        List<Category> list3=null;
        if(c.getLevel()>=1){
        	list1=categoryService.getListCategoryByLevel(1);
        }
        if(c.getLevel()>=2){
        	list2=categoryService.getListCategoryByLevel(2);
        	request.setAttribute("parentCategory", categoryService.getCategoryById(c.getParentId()));
        }
        if(c.getLevel()>=3){
        	list3=categoryService.getListCategoryByLevel(3);
        }
		request.setAttribute("categoryList1", list1);
		request.setAttribute("categoryList2", list2);
		request.setAttribute("categoryList3", list3);
		
		return "/backend/addCategory";
	}
	
	public ReturnResult modifyCategory(HttpServletRequest request,HttpServletResponse response){
		ReturnResult result=new ReturnResult();
		
		Integer parentId=0;
        String id=request.getParameter("id");
        String categoryLevel1=request.getParameter("categoryLevel1");
        String categoryLevel2=request.getParameter("categoryLevel2");
        
        String name=request.getParameter("name");
        String level=request.getParameter("level");
        if(level.equals("1")){
            parentId =0;
        }else if(level.equals("2")){
            parentId =Integer.parseInt(categoryLevel1);
        }else{
            parentId =Integer.parseInt(categoryLevel2);
        }
        
        Category c  =new Category();
        c.setId(Integer.parseInt(id));
        c.setParentId(parentId);
        c.setName(name);
        c.setLevel(Integer.parseInt(level));
        categoryService.updateCategory(c);
        result.returnSuccess();
		return result;
	}

}
