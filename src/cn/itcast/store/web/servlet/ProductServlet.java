package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/11 11:51
 * @description：
 * @modified By：
 * @version: $
 */
public class ProductServlet extends BaseServlet {

    //通过id查询详情      用于显示最热商品、最新商品
    public String findById(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        //1 接收参数
        String pid=req.getParameter("pid");

        //2 通知service进行查询
        ProductService productService=new ProductServiceImp();
        Product product=productService.findById(pid);

        //3.1 将查询结果存放作用域
        req.setAttribute("product",product );
        //3.2 页面跳转
        return "/jsp/product_info.jsp";
    }

    //通过cid查询详情      用于分类查询
    public String findByCid(HttpServletRequest req, HttpServletResponse resp) throws SQLException {
        System.out.println("********");
        //1 接收参数
        //1.1 分类id      cid
        String cid=req.getParameter("cid");
        //1.2 当前页
        int pageNumber=1;
        try {
            //没有参数，或参数错误，使用默认值1
            pageNumber=Integer.parseInt(req.getParameter("num"));
            System.out.println(pageNumber);
        }  catch (Exception e){
        }
        //1.3每页显示个数
        int pageSize=12;       //固定值，可以通过请求参数获得

        //2 调用业务层
        ProductService productService=new ProductServiceImp();
        PageModel pageModel=productService.findByCid(cid,pageNumber,pageSize);
        System.out.println(pageModel.list);
        //3.1 将查询结果存放作用域
        req.setAttribute("pageModel",pageModel);
        //3.2 页面跳转
        return "/jsp/product_list.jsp";
    }
}
