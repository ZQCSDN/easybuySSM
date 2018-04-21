package com.easybuy.servlet.backend;

import com.easybuy.entity.Category;
import com.easybuy.service.category.CategoryService;
import com.easybuy.service.category.CategoryServiceImpl;
import com.easybuy.servlet.web.AbstractServlet;
import com.easybuy.util.Page;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/backend/categoryServlet")
public class CategoryServlet extends AbstractServlet {

	private CategoryService categoryService;
	
	public void init() throws ServletException {
		this.categoryService=new CategoryServiceImpl();
	}
	
	public Class getServletClass() {
		return CategoryServlet.class;
	}

	public String getListCategoryByLevel(HttpServletRequest request,
				HttpServletResponse response) throws Exception{
		String level=request.getParameter("level");		
		
		//分页功能
		int totalCount=categoryService.getTotalCount();
		//System.out.println("totalCount:"+totalCount);
		//创建Page对象的时候，totalCount\totalPageCount都初始化成功
		Page page=new Page(totalCount);
		String currentPageNoStr = request.getParameter("currentPageNo");
		//System.out.println(page.getCurrentPageNo());
		String pageSizeStr=request.getParameter("pageSize");
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
		List<Category> list=categoryService.getListCategoryByLevel(
				Integer.parseInt(level),page.getStartIndex(),page.getPageSize());
		request.setAttribute("categoryList", list);
		
		//取到数据后，转发到categoryList.jsp页面，这个页面在/backend下
		return "/backend/categoryList";
	}
	
	public String addCategory(HttpServletRequest request,
			HttpServletResponse response){
		//TODO
		Category c=new Category();
		String levelStr=request.getParameter("level");
		c.setLevel(Integer.parseInt(levelStr));
		
		return "";
	}
	
	

}
