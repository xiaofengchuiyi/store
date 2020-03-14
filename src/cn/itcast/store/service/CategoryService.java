package cn.itcast.store.service;

import cn.itcast.store.domain.Category;

import java.sql.SQLException;
import java.util.List;

public interface CategoryService {
    List<Category> getAllCats() throws SQLException;

    void addCategory(Category c) throws SQLException;
}
