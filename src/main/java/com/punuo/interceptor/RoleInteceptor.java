package com.punuo.interceptor;

import com.sun.org.apache.regexp.internal.RE;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lenovo on 2018/4/10.
 * @version 1.0
 */

public class RoleInteceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String roleName = (String) session.getAttribute("roleName");
        System.out.println("-------" + roleName);
        if ("admin".equals(roleName)){
            return true;
        }else{
            request.getRequestDispatcher("/noAuthority").forward(request,response);
            return false;
        }
    }
}
