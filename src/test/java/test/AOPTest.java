package test;

import com.easybuy.entity.Category;
import com.easybuy.service.category.CategoryService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AOPTest {

	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");	
		//如果不调用context对象的任何方法，是不会启动切面的方法的
		CategoryService cs=(CategoryService)context.getBean("categoryService");
		Category c=cs.getCategoryById(2);
		System.out.println(c.getName());
	}

}
