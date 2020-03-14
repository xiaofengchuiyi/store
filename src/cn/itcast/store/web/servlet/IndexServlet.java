package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Category;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.CategoryService;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.CategoryServiceImp;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/10 16:49
 * @description：
 * @modified By：
 * @version: $
 */
public class IndexServlet extends BaseServlet {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        //在header.jsp中，通过ajax得到分类信息，故将此段代码注释
        /*//调用业务层功能:获取全部分类信息，返回集合
        CategoryService categoryService=new CategoryServiceImp();
        List<Category> list=categoryService.getAllCats();
        //将返回的集合放入req
        req.setAttribute("allCats", list);*/

        ProductService productService=new ProductServiceImp();

        //1.1查询热门商品(最热最新)
        List<Product> hotList=productService.findByHot();
        //1.2查询最新商品
        List<Product> newList=productService.findByNew();

        //2.将查询结果存放
        req.setAttribute("hotList",hotList );
        req.setAttribute("newList",newList );

        //转发到真实的首页
        return "/jsp/index.jsp";
    }
}
