package cn.itcast.store.service;

import cn.itcast.store.domain.User;

import java.sql.SQLException;

public interface UserService {
    void userRegist(User user) throws SQLException;

    boolean userActive(String code) throws SQLException;

    User userLogin(User user) throws SQLException;


    User findByUsername(String username) throws SQLException;
}
