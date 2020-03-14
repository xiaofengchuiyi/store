package cn.itcast.store.web.filter;

import cn.itcast.store.domain.User;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import cn.itcast.store.service.UserService;
import cn.itcast.store.service.serviceImp.UserserviceImp;
import cn.itcast.store.utils.CookUtils;

/**
 * @author ：mmzs
 * @date ：Created in 2020/2/10 10:31
 * @description：
 * @modified By：
 * @version: $
 */
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //0  强转
        HttpServletRequest request=(HttpServletRequest)servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;

        //如果登陆页直接放行
        String servletPath=request.getServletPath();
        if (servletPath.startsWith("/UserServlet" ));{
            String method=request.getParameter("method");
            if ("loginUI".equals(method)){
                filterChain.doFilter(request,response );
                return;
            }
        }

        //1  用户登陆信息
        User loginUser=(User) request.getSession().getAttribute("loginUser");

        //2.如果已经登陆，放行，不需要自动登陆
        if(loginUser!=null){
            filterChain.doFilter(request,response );
            return;
        }

        //3  自动登陆  cookie信息
        Cookie userCookie=CookUtils.getCookieByName("autoLoginCookie", request.getCookies());

        //4  判断自动登陆cookie是否存在，如果没有cookie，不需要自动
        if(userCookie==null){
            filterChain.doFilter(request,response );
            return;
        }

        //5  通过用户cookie中记录信息，查询用户
        //5.1  获得用户信息
        String[] u=userCookie.getValue().split("@");
        String username=u[0];
        String password=u[1];
        User user=new User(username,password);
        try{
            //5.2  执行登录
            UserService userService=new UserserviceImp();
            loginUser=userService.userLogin(user);
            //6  没有查询到
            if(loginUser==null){
                filterChain.doFilter(request, response);
                return;
            }
            //7  自动登陆
            request.getSession().setAttribute("loginUser",loginUser );
            //放行
            filterChain.doFilter(request,response );
        } catch (SQLException e) {
            System.out.println("自动登陆异常，自动忽略");
        }

    }

    @Override
    public void destroy() {

    }


}
