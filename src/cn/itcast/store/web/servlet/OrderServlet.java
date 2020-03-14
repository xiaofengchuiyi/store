package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.*;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.service.serviceImp.OrderServiceImp;
import cn.itcast.store.test.AlipayConfig;
import cn.itcast.store.utils.PaymentUtil;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import org.apache.taglibs.standard.lang.jstl.test.beans.PublicBean2a;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/13 9:00
 * @description：
 * @modified By：
 * @version: $
 */
public class OrderServlet extends BaseServlet {

    //添加订单
    public String saveOrder(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {

        //1 获得数据
        //1.1 获得购物车
        Cart cart=(Cart)req.getSession().getAttribute("cart");
        //1.2 获得登陆用户
        User loginUser=(User)req.getSession().getAttribute("loginUser");
        System.out.println(loginUser);

        if(loginUser==null){
            req.setAttribute("msg","请先登录，再购买" );
            return "jsp/login.jsp";
        }

        //2 封装数据
        Order order=new Order();
        //2.1 服务器自动生成
        order.setOid(UUIDUtils.getId());
        order.setState(1);   //1:表示未付款
        order.setOrdertime(new Date());
        //2.2设置总金额
        order.setTotal(cart.getTotal());
        //2.3设置所属用户
        order.setUser(loginUser);
        //2.4设置订单项的集合
        //遍历购物车中的购物项   修改返回值  public Collection<CartItem> getCartItems()
        for (CartItem cartItem:cart.getCartItems()){
            //将购物车中的数据转换到订单详情对象
            OrderItem orderItem=new OrderItem();
            orderItem.setItemid(UUIDUtils.getId());
            orderItem.setTotal(cartItem.getSubTotal());
            orderItem.setQuantity(cartItem.getNum());
            orderItem.setProduct(cartItem.getProduct());

            //设置当前的订单项属于哪个订单：程序的角度体现订单项和订单对应关系
            orderItem.setOrder(order);
            order.getList().add(orderItem);
            System.out.println(orderItem);

        }

        System.out.println("保存订单");

        //2.5调用业务层:保存订单
        OrderService orderService=new OrderServiceImp();
        orderService.saveOrder(order);

        //2.6清空购物车
        cart.clearCar();
        //3 将订单放入req
        req.setAttribute("order", order);
        //4 页面跳转
        return "/jsp/order_info.jsp";
    }

    //我的订单   订单查询
    public String findByUid(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //1.1  获得当前页
        int curNumber=1;
        try{
            curNumber=Integer.parseInt(req.getParameter("num"));
        }   catch(Exception e){
        }
        int pageSize=3;   //暂时固定值
        //1.2  获得当前用户
        User loginUser=(User)req.getSession().getAttribute("loginUser");

        //2 通过业务层查询
        OrderService orderService=new OrderServiceImp();
        PageModel pm=orderService.findByUid(loginUser,curNumber);

        //3 显示数据
        req.setAttribute("pageModel", pm);    //page和页面对应

        //4 转发
        return "/jsp/order_list.jsp";
    }

    //订单详情  订单查询
    public String findByOid(HttpServletRequest req,HttpServletResponse resp) throws IllegalAccessException, SQLException, InvocationTargetException {
        //获取到订单oid
        String oid=req.getParameter("oid");
        //调用业务层功能：根据订单编号查询订单
        OrderService orderService=new OrderServiceImp();
        Order order=orderService.findByOid(oid);
        //将订单放入requst
        req.setAttribute("order",order);
        //转发
        return "/jsp/order_info.jsp";
    }

    //支付订单
    public String payOrder(HttpServletRequest req,HttpServletResponse resp) throws Exception{
        //获取订单oid，收货人地址，姓名，电话，银行
        String oid=req.getParameter("oid");
        String address=req.getParameter("address");
        String name=req.getParameter("name");
        String telephone=req.getParameter("telephone");
        String pd_FrpId=req.getParameter("pd_FrpId");

        //更新订单上收货人的地址，姓名，电话
        OrderService orderService=new OrderServiceImp();
        Order order=orderService.findByOid(oid);
        order.setName(name);
        order.setName(telephone);
        order.setName(address);
        orderService.updateOrder(order);


        // 使用重定向：
        resp.sendRedirect("/store/jsp/pay_page.jsp");
        return null;

    }

    //callBack
    public String callBack(HttpServletRequest req,HttpServletResponse resp) throws IOException, IllegalAccessException, SQLException, InvocationTargetException {
        //接收易宝支付的数据

        // 验证请求来源和数据有效性
        // 阅读支付结果参数说明
        // System.out.println("==============================================");
        String p1_MerId = req.getParameter("p1_MerId");
        String r0_Cmd = req.getParameter("r0_Cmd");
        String r1_Code = req.getParameter("r1_Code");
        String r2_TrxId = req.getParameter("r2_TrxId");
        String r3_Amt = req.getParameter("r3_Amt");
        String r4_Cur = req.getParameter("r4_Cur");
        String r5_Pid = req.getParameter("r5_Pid");
        String r6_Order = req.getParameter("r6_Order");
        String r7_Uid = req.getParameter("r7_Uid");
        String r8_MP = req.getParameter("r8_MP");
        String r9_BType = req.getParameter("r9_BType");
        String rb_BankId = req.getParameter("rb_BankId");
        String ro_BankOrderId = req.getParameter("ro_BankOrderId");
        String rp_PayDate = req.getParameter("rp_PayDate");
        String rq_CardNo = req.getParameter("rq_CardNo");
        String ru_Trxtime = req.getParameter("ru_Trxtime");

        // hmac
        String hmac = req.getParameter("hmac");
        // 利用本地密钥和加密算法 加密数据
        String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";

        //保证数据合法性
        boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
                r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
                r8_MP, r9_BType, keyValue);
        if (isValid) {
            // 有效
            if (r9_BType.equals("1")) {
                // 浏览器重定向

                //如果支付成功，更新订单状态
                OrderService orderService=new OrderServiceImp();
                Order order= orderService.findByOid(r6_Order);
                order.setState(2);
                orderService.updateOrder(order);

                //向request放入提示信息
                req.setAttribute("msg", "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);

                //转发到/jsp/info.jsp
                return "/jsp/info.jsp";

                /*resp.setContentType("text/html;charset=utf-8");
                resp.getWriter().println(
                        "支付成功！订单号：" + r6_Order + "金额：" + r3_Amt);*/

            } else if (r9_BType.equals("2")) {
                // 修改订单状态:
                // 服务器点对点，来自于易宝的通知
                System.out.println("收到易宝通知，修改订单状态！");//
                // 回复给易宝success，如果不回复，易宝会一直通知
                resp.getWriter().print("success");
            }

        } else {
            throw new RuntimeException("数据被篡改！");
        }

        return null;
    }
}
