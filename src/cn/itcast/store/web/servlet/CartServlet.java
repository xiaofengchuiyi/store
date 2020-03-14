package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.Cart;
import cn.itcast.store.domain.CartItem;
import cn.itcast.store.domain.Product;
import cn.itcast.store.service.ProductService;
import cn.itcast.store.service.serviceImp.ProductServiceImp;
import cn.itcast.store.web.base.BaseServlet;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/12 19:46
 * @description：
 * @modified By：
 * @version: $
 */
public class CartServlet extends BaseServlet {

    //向购物车里面添加商品
    public String addCartItemToCart(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException {
        System.out.println("加入购物车");
        //从session获取购物车
        Cart cart=(Cart)req.getSession().getAttribute("cart");
        if (cart==null){
            //如果获取不到，创建购物车对象，放在session
            cart=new Cart();
            req.getSession().setAttribute("cart", cart);
        }

        //如果获取到，使用即可
        //获取到商品的id，数量
        String pid = req.getParameter("pid");
        int num = Integer.parseInt(req.getParameter("quantity"));
        System.out.println("加入购物车的商品数量" + num);
        //通过商品id查询到商品对象
        ProductService productService = new ProductServiceImp();
        Product product = productService.findById(pid);
        //获取到待购买的购物项
        CartItem cartItem = new CartItem();
        cartItem.setNum(num);
        cartItem.setProduct(product);

        //调用购物车上的方法
        cart.addCartItemToCart(cartItem);

        //重定向
        resp.sendRedirect("/store/jsp/cart.jsp");

        return null;
    }

    //清除购物车
    public String clearCart(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException{
        //1 获得购物车
        Cart cart=(Cart)req.getSession().getAttribute("cart");

        //2 清除购物车
        cart.clearCar();

        //3 重定向到购物车
        resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");

        return null;
    }

    //移除商品
    public String removeCart(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException{
        //1 获得购物车   删除商品的pid
        Cart cart=(Cart)req.getSession().getAttribute("cart");
        String pid=req.getParameter("pid");

        //2 清除购物项
        cart.removeCarItem(pid);

        //3 重定向到购物车
        resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");

        return null;
    }
}
