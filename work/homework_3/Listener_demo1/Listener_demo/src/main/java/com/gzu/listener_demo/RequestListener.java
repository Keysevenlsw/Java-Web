package com.gzu.listener_demo;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletRequestEvent;
import jakarta.servlet.ServletRequestListener;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener("/*")
public class RequestListener implements ServletRequestListener {
    // 创建一个LOGGER实例来进行日志记录。
    private static final Logger LOGGER = Logger.getLogger(RequestListener.class.getName());
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 在请求建立的时候记录开始时间。
    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        sre.getServletRequest().setAttribute("startTime", System.currentTimeMillis());
    }

    // 在请求结束的时候记录结束时间，并用结束时间减去开始时间得到请求处理时间。
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        long endTime = System.currentTimeMillis();
        long duration = endTime - (Long) sre.getServletRequest().getAttribute("startTime");
        logRequestDetails((HttpServletRequest) sre.getServletRequest(), endTime, duration);
    }

    // 创建一个ogRequestDetails方法用来获取用户IP、方法、URL、查询字符串、User-Agent、请求处理时间，并将这些信息存储进日志。
    private void logRequestDetails(HttpServletRequest request, long endTime, long duration) {
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("[").append(DATE_FORMAT.format(new Date())).append("] ");
        logMessage.append("Request ended with details: \n");
        logMessage.append("\tClient IP: ").append(request.getRemoteAddr()).append("\n");
        logMessage.append("\tMethod: ").append(request.getMethod()).append("\n");
        logMessage.append("\tURI: ").append(request.getRequestURI()).append("\n");
        logMessage.append("\tQuery String: ").append(request.getQueryString() != null ? request.getQueryString() : "None").append("\n");
        logMessage.append("\tUser-Agent: ").append(request.getHeader("User-Agent")).append("\n");
        logMessage.append("\tDuration: ").append(duration).append(" ms\n");

        LOGGER.log(Level.INFO, logMessage.toString());
    }
}


