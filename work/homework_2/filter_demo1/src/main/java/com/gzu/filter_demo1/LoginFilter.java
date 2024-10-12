package com.gzu.filter_demo1;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 创建一个排除列表
        String[] exc_list = { "/login", "register", "/public", "/index"};

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 用一个字符串存储请求url
        String url = request.getRequestURI();

        // 创建一个布尔变量用来判断url是否在排除列表里
        boolean isPublicResource = false;
        for (String exc : exc_list) {
            if (url.contains(exc)) {
                isPublicResource = true;
                break;
            }
        }

        // 如果url在排除列表里，则放行，反之，则判断用户的session里是否有登录信息
        if (isPublicResource) {
            filterChain.doFilter(request, response);
        }else {
            // 获取session信息
            HttpSession session = request.getSession();
            Object user = session.getAttribute("user");

            // 如果session信息不为空就放行，反之就跳转login页面
            if (user != null) {
                filterChain.doFilter(request, response);
            }else {
                response.sendRedirect("login.html");
            }
        }

    }
}
