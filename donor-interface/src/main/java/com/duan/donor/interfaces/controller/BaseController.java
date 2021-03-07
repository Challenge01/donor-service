package com.duan.donor.interfaces.controller;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 基本控制器
 */
public class BaseController {

    public HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    /**
     * 获取已登录用户信息的token（前端存放）
     * @return
     */
    public String getToken(){
        HttpServletRequest request = getRequest();
        return request.getHeader("Authorization");
    }
}