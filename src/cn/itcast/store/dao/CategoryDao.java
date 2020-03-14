package cn.itcast.store.dao;

import cn.itcast.store.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryDao {
    List<Category> getAllCats() throws SQLException;

    void addCategory(Category c) throws SQLException;
}
