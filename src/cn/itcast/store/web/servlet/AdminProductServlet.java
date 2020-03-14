package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author ：mmzs
 * @date ：Created in 2020/3/5 16:38
 * @description：
 * @modified By：
 * @version: $
 */
public class AdminProductServlet extends BaseServlet {
    //findAllProductsWithPage
    public String findAllProductsWithPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        //获取当前页
        int curNum=Integer.parseInt(req.getParameter("num"));
        //调用业务层查全部商品信息返回PageModel
        ProductService productService=new ProductServiceImp();
        PageModel pageModel=productService.findAllProductsWithPage(curNum);
        //将PageModel放入request
        req.setAttribute("pageModel",pageModel );
        //转发到/admin/product/list.jsp
        return "/admin/product/list.jsp";
    }

}
