package cn.itcast.store.dao.daoImp;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.domain.User;
import cn.itcast.store.utils.JDBCUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/8 16:27
 * @description：
 * @modified By：
 * @version: $
 */
public class UserDaoImp implements UserDao {
    @Override
    public void userRegist(User user) throws SQLException {
        String sql="INSERT INTO USER VALUES(?,?,?,?,?,?,?,?,?,?)";
        QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
        Object[] params={user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
        qr.update(sql,params);
    }

    @Override
    public User userActive(String code) throws SQLException {
        String sql="select * from user where code=?";
        QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
        System.out.println("------");
        User user=qr.query(sql, new BeanHandler<User>(User.class),code);
        System.out.println("找到user");
        return user;
    }

    @Override
    public void updateUser(User user) throws SQLException {
        String sql="update user set username=?,password=?,name=?,email=?,telephone=?,birthday=?,sex=?,state=?,code=? where uid=?";
        QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
        Object[] params={user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
        qr.update(sql, params);
    }

    @Override
    public User userLogin(User user) throws SQLException {
        String sql="select * from user where username=? and password=?";
        QueryRunner qr=new QueryRunner(JDBCUtils.getDataSource());
        return qr.query(sql, new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
    }

    @Override
    public User findByUsername(String username) throws SQLException {
        String sql="select * from user where username=?";
        QueryRunner queryRunner=new QueryRunner(JDBCUtils.getDataSource());
        User existUser=queryRunner.query(sql, new BeanHandler<User>(User.class),username);
        return existUser;
    }
}
