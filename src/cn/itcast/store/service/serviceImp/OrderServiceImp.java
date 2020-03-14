package cn.itcast.store.service.serviceImp;

import cn.itcast.store.dao.OrderDao;

import cn.itcast.store.dao.daoImp.OrderDaoImp;
import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.OrderService;
import cn.itcast.store.utils.JDBCUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/13 9:02
 * @description：
 * @modified By：
 * @version: $
 */
public class OrderServiceImp implements OrderService {

    @Override
    public void saveOrder(Order order) throws SQLException {
        //保存订单和订单下所有的订单项（同时成功，失败）
        //用到事务

        Connection conn= null;
        try{
            //获取连接
            conn= JDBCUtils.getConnection();

            //开启事务
            conn.setAutoCommit(false);

            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");

            //保存订单
            OrderDao orderDao=new OrderDaoImp();
            orderDao.saveOrder(conn,order);     //保证同一个连接

            System.out.println(order.getList());
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$");

            //保存订单项
            for (OrderItem item:order.getList()) {
                orderDao.saveOrderItem(conn,item);
            }
            //提交
            conn.commit();
        }  catch (Exception e){
            //回滚
            conn.rollback();
        }
    }

    @Override
    public PageModel findByUid(User loginUser, int curNumber) throws SQLException, InvocationTargetException, IllegalAccessException {
        //1 创建PageModel对象，目的：计算并且携带分页参数
        int totalRecords=new OrderDaoImp().getTotalRecords(loginUser);
        System.out.println(totalRecords);
        PageModel pm=new PageModel(curNumber, totalRecords, 3);
        //2 关联集合
        List list=new OrderDaoImp().findByUid(loginUser,pm.getStartIndex(),pm.getPageSize());
        pm.setList(list);
        //3 关联url
        pm.setUrl("OrderServlet?method=findByUid");
        return pm;
    }

    @Override
    public Order findByOid(String oid) throws IllegalAccessException, SQLException, InvocationTargetException {
        OrderDao orderDao=new OrderDaoImp();
        return orderDao.findByOid(oid);
    }

    @Override
    public void updateOrder(Order order) throws SQLException {
        OrderDao orderDao=new OrderDaoImp();
        orderDao.updateOrder(order);
    }
}
