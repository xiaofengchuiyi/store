package cn.itcast.store.service;

import cn.itcast.store.domain.Order;
import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.User;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

public interface OrderService {
    void saveOrder(Order order) throws SQLException;

    PageModel findByUid(User loginUser, int pageNumber) throws SQLException, InvocationTargetException, IllegalAccessException;

    Order findByOid(String oid) throws IllegalAccessException, SQLException, InvocationTargetException;

    void updateOrder(Order order) throws SQLException;
}
