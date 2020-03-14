package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Category;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ：mmzs
 * @date ：Created in 2020/3/5 10:52
 * @description：
 * @modified By：
 * @version: $
 */
public class AdminCategoryServlet extends BaseServlet {
    public String findAllCats(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        //获取全部分类信息
        CategoryService categoryService=new CategoryServiceImp();
        List<Category> list=categoryService.getAllCats();
        //全部信息放入request
        req.setAttribute("allCats",list);
        //转发到/admin/category/list.jsp
        return "/admin/category/list.jsp";
    }

    //添加分类
    //addCategoryUI
    public String addCategoryUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException{
        return "/admin/category/add.jsp";
    }
    //addCategory
    public String addCategory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException{
        //获取分类名称
         String cname=req.getParameter("cname");
        //创建分类ID
        String id= UUIDUtils.getId();
        Category c=new Category();
        c.setCid(id);
        c.setCname(cname);
        //调用业务层添加分类功能
        CategoryService categoryService=new CategoryServiceImp();
        categoryService.addCategory(c);

        //重定向到查询全部分类信息
        resp.sendRedirect("/store/AdminCategoryServlet?method=findAllCats");
        return null;
    }

}
