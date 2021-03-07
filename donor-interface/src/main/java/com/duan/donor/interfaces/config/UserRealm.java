package com.duan.donor.interfaces.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duan.donor.business.entiy.User;
import com.duan.donor.business.service.IUserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;


public class UserRealm extends AuthorizingRealm {

    @Autowired
    private IUserService userService;

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //给资源进行授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //添加资源的授权字符串,这个字符串要和授权拦截的字符串一样
        info.addStringPermission("user:add");
        return info;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //编写shiro判断逻辑，判断用户名和密码
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",token.getUsername());
        User user = userService.getOne(wrapper);
        //判断用户名
        if(user == null){
            //用户名不存在，此时shiro会帮我们抛出UnknownAccountException
            return null;
        }

        //判断密码,shiro会帮我们判断密码是否和数据库中的密码一样
        return new SimpleAuthenticationInfo("",user.getPassword(),"");
    }
}