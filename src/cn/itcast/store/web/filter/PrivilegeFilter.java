package cn.itcast.store.web.filter;

import cn.itcast.store.domain.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 权限过滤器    登陆才能操作
 */
public class PrivilegeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest)servletRequest;
        User loginUser=(User)req.getSession().getAttribute("loginUser");
        if (loginUser==null){
            req.setAttribute("msg", "您还没有登录！没有权限访问");
            req.getRequestDispatcher("/jsp/info.jsp").forward(req,servletResponse );
        }
        filterChain.doFilter(req,servletResponse );
    }

    @Override
    public void destroy() {

    }
}
