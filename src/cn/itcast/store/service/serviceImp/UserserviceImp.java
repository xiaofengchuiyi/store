package cn.itcast.store.service.serviceImp;

import cn.itcast.store.dao.UserDao;
import cn.itcast.store.dao.daoImp.UserDaoImp;
import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;

import java.sql.SQLException;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/8 16:28
 * @description：
 * @modified By：
 * @version: $
 */
public class UserserviceImp implements UserService {

    //实现注册功能
    @Override
    public void userRegist(User user) throws SQLException {
        UserDao UserDao=new UserDaoImp();
        UserDao.userRegist(user);
    }

    //实现激活功能
    @Override
    public boolean userActive(String code) throws SQLException {
        UserDao UserDao=new UserDaoImp();
        //对DB发送select * from user where code=？
        User user=UserDao.userActive(code);

        if(user!=null){
            //可以根据激活码查询到一个用户
            //在内存中，修改用户的状态，清除激活码
            System.out.println("*********");
            user.setState(1);
            user.setCode(null);
            System.out.println("更改内存完毕");
            //对数据库执行一次真实的更新操作
            //update user set state=1,code=null where uid=?
            //写个通用的 update user set username=?,password=?,name=?......where uid=?
            UserDao.updateUser(user);
            System.out.println("更改数据库完毕");
            return true;
        }else{
            //不可以根据激活码查询到一个用户
            return false;
        }
    }

    //实现登陆功能   从数据库查找信息,进行判断
    @Override
    public User userLogin(User user) throws SQLException {
        //此处：可以利用异常在模块之间传递数据

        UserDao UserDao=new UserDaoImp();
        //select * from user where username=? and password=?
        User uu=UserDao.userLogin(user);
        if (uu==null){
            throw new RuntimeException("账户名或密码有误");
        }else if (uu.getState()==0){
            throw new RuntimeException("用户未激活");
        }else{
            return uu;
        }
    }

    //实现自动登陆功能     通过用户名在数据库进行查找
    @Override
    public User findByUsername(String username) throws SQLException {
        return new UserDaoImp().findByUsername(username);
    }
}
