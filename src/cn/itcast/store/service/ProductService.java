package cn.itcast.store.service;

import cn.itcast.store.domain.PageModel;
import cn.itcast.store.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService  {

    //热门商品
    List<Product> findByHot() throws SQLException;

    //最新商品
    List<Product> findByNew() throws SQLException;

    //通过id查询详情
    Product findById(String pid) throws SQLException;

    PageModel findByCid(String cid, int pageNumber, int pageSize) throws SQLException;

    PageModel findAllProductsWithPage(int curNum) throws SQLException;
}
