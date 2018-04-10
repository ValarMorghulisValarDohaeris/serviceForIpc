package com.punuo.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lenovo on 2018/4/9.
 * @version 1.0
 */

/**
 * 该拦截器用于拦截除个别无需拦截外的所有请求，用于判断用户是否登录。
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String userName = (String) session.getAttribute("userName");
        String userId = (String) session.getAttribute("userId");
//        System.out.println("-----" + userId + userName);
        if(userName == null){
            request.getRequestDispatcher("/notLogin1").forward(request,response);
            return false;
        }else if (userId == null){
            if(request.getRequestURI().equals("/login2")){
                return true;
            }else{
                request.getRequestDispatcher("/notLogin2").forward(request,response);
                return false;
            }
        }

        return true;
    }
}
