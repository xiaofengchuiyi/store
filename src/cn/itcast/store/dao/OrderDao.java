package cn.itcast.store.dao;

import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.OrderItem;
import cn.itcast.store.domain.User;
import cn.itcast.store.web.base.BaseServlet;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface OrderDao  {

    void saveOrder(Connection conn, Order order) throws SQLException;


    void saveOrderItem(Connection conn, OrderItem item) throws SQLException;

    int getTotalRecords(User loginUser) throws SQLException;

    List findByUid(User loginUser, int startIndex, int pageSize) throws SQLException, InvocationTargetException, IllegalAccessException;

    Order findByOid(String oid) throws SQLException, InvocationTargetException, IllegalAccessException;

    void updateOrder(Order order) throws SQLException;
}
