package cn.itcast.store.web.servlet;

import cn.itcast.store.domain.User;
import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serviceImp.UserserviceImp;
import cn.itcast.store.utils.MailUtils;
import cn.itcast.store.utils.MyBeanUtils;
import cn.itcast.store.utils.UUIDUtils;
import cn.itcast.store.web.base.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/8 16:31
 * @description：
 * @modified By：
 * @version: $
 */
public class UserServlet extends BaseServlet {

    //跳转到注册界面
    public String registUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return "/jsp/register.jsp";
    }

    //跳转到登陆界面
    public String loginUI(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        return "/jsp/login.jsp";
    }

    //注册
    public String UserRegist(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //接收表单参数
        Map<String,String[]> map=req.getParameterMap();
        User user=new User();

        //反射，把map中的数据传给user对象  时间格式数据的转化（birthday）
        MyBeanUtils.populate(user, map);
        //为用户的其他属性赋值
        user.setUid(UUIDUtils.getId());
        user.setState(0);
        user.setCode(UUIDUtils.getCode());

        System.out.println(user);

        /*Set<String> keySet=map.keySet();
        Iterator<String> iterator=keySet.iterator();
        while(iterator.hasNext()){
            String str=iterator.next();
            System.out.println(str);
            String[] strs=map.get(str);
            for (String string:strs){
                System.out.println(string);
            }
            System.out.println();
        }*/

        //调用业务层注册功能
        UserService UserService=new UserserviceImp();
        try {
            UserService.userRegist(user);
            //注册成功，向用户邮箱发送信息，跳转到提示界面
            //发送邮件
            MailUtils.sendMail(user.getEmail(), user.getCode());
            req.setAttribute("msg", "用户注册成功，请激活");
        } catch (Exception e) {
            //注册失败，跳转到提示界面
            req.setAttribute("msg", "用户注册失败，请重新注册");
        }
        return "/jsp/info.jsp";
    }

    //邮件激活
    public String active(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        //获取激活码
        String code=req.getParameter("code");
        //调取业务层激活功能
        UserService UserService=new UserserviceImp();
        boolean flag=UserService.userActive(code);

        System.out.println("激活显示");

        //进行激活信息提示
        if (flag==true){
            //用户激活成功，向req放入提示信息，转发到登陆界面
            req.setAttribute("msg", "用户激活成功，请登录");
            System.out.println("激活成功");
            return "/jsp/login.jsp";
        }else{
            //用户激活失败，向req放入提示信息，转发到提示页面
            req.setAttribute("msg", "用户激活失败，请重新激活");
            System.out.println("激活失败");
            return "/jsp/info.jsp";
        }
    }

    //登陆
    public String login(HttpServletRequest req,HttpServletResponse resp){
        //1.封装数据
        User user=new User();
        MyBeanUtils.populate(user, req.getParameterMap());

        //调用业务层登陆功能
        UserService UserService=new UserserviceImp();
        User user02=null;
        try{
            //select * from user where username=? and password=?
            user02=UserService.userLogin(user);

            //##自动登陆    start
            String autoLogin=req.getParameter("autoLoin");
            if("1".equals(autoLogin)){
                //如果勾选发送cookie
                Cookie autoLoginCookie=new Cookie("autoLoginCookie", user02.getUsername()
                +"@"+user02.getPassword());
                autoLoginCookie.setPath("/");
                autoLoginCookie.setMaxAge(60*60*24*7);
                resp.addCookie(autoLoginCookie);
            }else{
                //删除Cookie
                Cookie autoLoginCookie=new Cookie("autoLoginCookie", "");
                autoLoginCookie.setPath("/");
                autoLoginCookie.setMaxAge(0);
                resp.addCookie(autoLoginCookie);
            }
            //##自动登陆  end

            //##记住用户名 start
            String rememberme=req.getParameter("rememberme");
            if ("1".equals(rememberme)){
                //如果勾选发送cookie
                Cookie remembermeCookie=new Cookie("remembermeCookie", user02.getUsername());
                remembermeCookie.setPath("/");
                remembermeCookie.setMaxAge(60*60*24*7);
                resp.addCookie(remembermeCookie);
            }else{
                //删除Cookie
                Cookie remembermeCookie=new Cookie("remembermeCookie", "");
                remembermeCookie.setPath("/");
                remembermeCookie.setMaxAge(0);
                resp.addCookie(remembermeCookie);
            }
            //##记住用户名 stop

            //用户登陆成功，将用户信息放入session中
            req.getSession().setAttribute("loginUser", user02);
            resp.sendRedirect("/store/index.jsp");              //"/index.jsp"是不行的
            return null;
        }catch (Exception e){
            //用户登陆失败
            String msg=e.getMessage();
            System.out.println(msg);
            //向request放入失败的信息
            req.setAttribute("msg", msg);
            return "/jsp/login.jsp";
        }
    }

    //退出
    public String logOut(HttpServletRequest req,HttpServletResponse resp) throws IOException {
        //1.将session用户信息清除
        req.getSession().removeAttribute("loginUser");
        //2.重定向到首页
        resp.sendRedirect(req.getContextPath()+"/UserServlet?method=loginUI");
        //3.不使用BaseServlet的请求转发
        return null;
    }

    //ajax   注册时检查用户名是否存在
    public void checkUsername(HttpServletRequest req,HttpServletResponse resp) throws ServletException, IOException, SQLException {
        //接收文本框的值
        String username=req.getParameter("username");
        System.out.println(username);
        //调用业务层查询
        UserService userService=new UserserviceImp();
        User existUser=userService.findByUsername(username);
        //判断
        if (existUser==null){
            //用户名没有使用
            resp.getWriter().println(1);
        }else{
            //用户名已经被使用
            resp.getWriter().println(2);
        }
    }

}
