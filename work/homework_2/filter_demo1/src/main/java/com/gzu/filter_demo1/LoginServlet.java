package com.gzu.filter_demo1;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // 判断账号密码是否一致，一致就放行，反之跳回login页面
        if ("admin".equals(username) && "123456".equals(password)) {
            req.getSession().setAttribute("user", username);
            resp.sendRedirect("welcome.html");
        }else {
            resp.sendRedirect("login.html");
        }
    }
}
