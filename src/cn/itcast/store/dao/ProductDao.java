package cn.itcast.store.dao;

import cn.itcast.store.domain.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {


    int findTotalRecords() throws SQLException;

    public List<Product> findByHot() throws SQLException;

    public List<Product> findByNew() throws SQLException;

    Product findById(String pid) throws SQLException;

    int findTotalRecordByCid(String cid) throws SQLException;

    List<Product> findByCid(String cid, int startIndex, int pageSize) throws SQLException;

    List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws SQLException;
}
