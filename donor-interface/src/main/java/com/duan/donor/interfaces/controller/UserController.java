package com.duan.donor.interfaces.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.duan.donor.business.bo.UserBo;
import com.duan.donor.business.entiy.User;
import com.duan.donor.business.service.IUserService;
import com.duan.donor.business.vo.UserVo;
import com.duan.donor.common.constant.RedisKey;
import com.duan.donor.common.util.AjaxResult;
import com.duan.donor.common.util.PhoneValidateUtil;
import com.duan.donor.common.util.ShiroMd5Util;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.JedisPool;
import javax.annotation.Resource;
import java.util.*;

@RestController
@RequestMapping("/user")
@Api(value="用户模块",tags={"用户操作"})
public class UserController extends BaseController{

    @Resource
    private IUserService userService;

    @Resource
    private JedisPool jedisPool;

    @Autowired
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 获取所有的用户
     * @return
     */
    @ApiOperation("获取所有的用户")
    @RequestMapping(value = "/getAllUser",method = RequestMethod.GET)
    private AjaxResult getAllUser(@RequestParam(value = "page",defaultValue = "1",required = false) int page,
                                  @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize) {

        //查询所有的用户
        PageInfo<UserVo> allUser = userService.getAllUser(page,pageSize);

        Map<String, Object> map = new HashMap<>();
        if (allUser  != null){
            map.put("count",allUser.getTotal());
            map.put("list",allUser.getList());
        }
        return AjaxResult.success(map);
    }

    /**
     * 注册用户
     * @return
     */
    @ApiOperation("注册用户")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    private AjaxResult register(@RequestBody UserBo userBo){
        HashMap<String, Object> map = new HashMap<>();

        try {
            if (StringUtils.isBlank(userBo.getPassword())){
                return AjaxResult.error("没有填写密码！");
            }

            if (StringUtils.isBlank(userBo.getTelPhone())){
                return AjaxResult.error("没有填写手机号！");
            }

            if (PhoneValidateUtil.isMobile(userBo.getTelPhone())){
                return AjaxResult.error("请输入正确的手机号！");
            }

            if (StringUtils.isBlank(userBo.getUserName())){
                return AjaxResult.error("没有填写昵称！");
            }

            //对新注册的用户密码进行加密操作
            String encryptPassword = ShiroMd5Util.encrypt(userBo.getPassword());

            //校验手机号和用户名是否已经被注册了
            String msg = isRegister(userBo);
            if (StringUtils.isNotBlank(msg)){
                return AjaxResult.error(msg);
            }

            //注册新用户
            User user = new User();
            BeanUtils.copyProperties(user,userBo);
            String loginToken = generateLoginToken(); //生成一个token
            user.setLoginToken(loginToken);
            user.setPassword(encryptPassword); //将加密后的密码给新注册的用户
            boolean create = userService.createUser(user);

            if (create){

                //注册成功，将用户的手机号和用户名放到缓存中
                jedisPool.getResource().sadd(RedisKey.USER_PHONE.getKey(),userBo.getTelPhone());
                jedisPool.getResource().sadd(RedisKey.USER_NAME.getKey(),userBo.getUserName());
                UserVo currentUser = userService.findByUserName(user.getUserName());
                if (currentUser == null) {
                    return AjaxResult.error("没有该用户");
                }

                //将登录生成的token放在redis中(存储的key要以字符串的格式，其他的格式会出现乱码的现象，是由于序列化方式导致的)
                jedisPool.getResource().setex(RedisKey.USER_LOGIN_TOKEN.getKey() + "" + currentUser.getUserId(),RedisKey.USER_LOGIN_TOKEN.getExpire(), loginToken);
                //如果没有异常，登录成功
                map.put("loginToken", loginToken);
                map.put("user",currentUser);
                return AjaxResult.success("注册成功",map);
            }else {
                return AjaxResult.success("注册失败！");
            }

        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.success("注册失败！");
        }
    }


    /**
     * 登录逻辑
     * @param userBo
     * @return
     */
    @ApiOperation("用户登录")
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public AjaxResult login(@RequestBody UserBo userBo){

        HashMap<String, Object> map = new HashMap<>();
        if (StringUtils.isBlank(userBo.getPassword())){
            return AjaxResult.error("没有填写密码！");
        }

        if (StringUtils.isBlank(userBo.getUserName())){
            return AjaxResult.error("没有填写昵称！");
        }

        String userName = userBo.getUserName();
        String password = userBo.getPassword();
        //获取subject
        Subject subject = SecurityUtils.getSubject();
        //封装用户数据
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userName, password);
        //执行登录方法
        try {
            //shiro登录密码校验
            subject.login(usernamePasswordToken);

            if (subject.isAuthenticated()) {
                UserVo currentUser = userService.findByUserName(userBo.getUserName());
                if (currentUser == null) {
                    return AjaxResult.error("没有该用户");
                }
                //从缓存中获取
                Object o = jedisPool.getResource().get(RedisKey.USER_LOGIN_TOKEN.getKey() + currentUser.getUserId());
                if (o != null){
                    String loginToken = (String) o;
                    map.put("loginToken", loginToken);
                    map.put("user",currentUser);
                    return AjaxResult.success("登录成功!",map);
                }else { //如果token失效，重新生成一个token
                    String loginToken = generateLoginToken();

                    //更新用户数据库中的token
                    User selectUserById = userService.getById(currentUser.getUserId());
                    selectUserById.setLoginToken(loginToken);
                    selectUserById.setUpdateTime(new Date());
                    userService.updateById(selectUserById);

                    //更新缓存中的token
                    jedisPool.getResource().set(RedisKey.USER_LOGIN_TOKEN.getKey() + currentUser.getUserId(),loginToken);
                    map.put("loginToken", loginToken);
                    map.put("user",currentUser);
                    return AjaxResult.success("登录成功!",map);
                }
            }
            return AjaxResult.success("登录失败!");
        }catch (UnknownAccountException e){
            //如果有异常，登录失败，如果发生UnknownAccountException，说明用户名不存在
            return AjaxResult.success("用户名不存在!");
        }catch (IncorrectCredentialsException e){
            //如果发生IncorrectCredentialsException，说明密码错误
            return AjaxResult.success("密码错误!");
        }catch (NullPointerException e){
            e.printStackTrace();
            return AjaxResult.success("登录失败!");
        }

    }


    /**
     * 根据用户名查询用户，避免用户名不重复
     * @param userName
     * @return
     */
    @ApiOperation("根据用户名查询用户")
    @RequestMapping(value = "/getByUserName",method = RequestMethod.GET)
    public AjaxResult getByUserName(@RequestParam(value = "userName") String userName){

        if (StringUtils.isBlank(userName)){
            return AjaxResult.error("没有填写用户昵称！");
        }

        try {
            UserVo user = userService.findByUserName(userName);
            return AjaxResult.success("获取用户成功!",user);
        }catch (Exception e){
            return AjaxResult.success("获取用户失败!");
        }

    }

    /**
     * 根据token获取用户信息
     * @return
     */
    @ApiOperation("根据token获取用户信息")
    @RequestMapping(value = "/getUserByToken",method = RequestMethod.GET)
    public AjaxResult getUserByToken(){

        String token = getToken();
        if (StringUtils.isBlank(token)){
            return AjaxResult.error("用户未登录！");
        }

        try {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.select("id","user_name","tel_phone","picture","email","gender"); //查询固定的字段
            wrapper.eq("login_token",token);
            User user = userService.getOne(wrapper);
            return AjaxResult.success("获取用户成功!",user);
        }catch (Exception e){

            return AjaxResult.success("获取用户失败!");
        }
    }


    /**
     * 获取到所有的用户信息，存储手机号和用户名到redis缓存中
     * @return
     */
    @ApiOperation("将用户名和手机号刷新到缓存中")
    @RequestMapping(value = "/brushUserInfo",method = RequestMethod.GET)
    public void brushUserInfo(){

        try {
            QueryWrapper<User> wrapper = new QueryWrapper<>();
            wrapper.select("id","user_name","tel_phone"); //查询固定的字段
            List<User> list = userService.list(wrapper);
            if (!list.isEmpty()){
                for (User user : list) {
                    jedisPool.getResource().sadd(RedisKey.USER_NAME.getKey() , user.getUserName()); //缓存用户名
                    jedisPool.getResource().sadd(RedisKey.USER_PHONE.getKey() , user.getTelPhone()); //缓存手机号
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 用户登出系统
     * @return
     */
    @ApiOperation("用户登出系统")
    @RequestMapping(value = "/loginOut",method = RequestMethod.GET)
    public AjaxResult loginOut(){


        String token = getToken();
        if (StringUtils.isBlank(token)){
            return AjaxResult.error("用户未登录！");
        }

        try {

            //从数据库中查询出用户的token
            User selectByToken = userService.getOne(new QueryWrapper<User>().eq("login_token", token));
            if (selectByToken == null){
                return AjaxResult.success("没有该用户!");
            }
            //清除缓存中用户的token
            jedisPool.getResource().del(RedisKey.USER_LOGIN_TOKEN.getKey() + selectByToken.getId());
            //清除数据库中的LoginToken,下次用户登录重新生成token放入缓存和数据库中
            selectByToken.setLoginToken(null);
            taskExecutor.execute(() -> {
                //异步清空用户的LoginToken
                userService.updateById(selectByToken);
            });

            return AjaxResult.success("用户退出成功!");
        }catch (Exception e){

            return AjaxResult.success("用户退出失败!");
        }

    }

    /**
     * 生成loginToken
     * @return
     */
    private String generateLoginToken() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 从缓存中校验手机号和用户名是否已经注册了
     * @param userBo
     * @return
     */
    private String isRegister(UserBo userBo) {
        //根据用户名查询用户信息
        Boolean userNameSismember = jedisPool.getResource().sismember(RedisKey.USER_NAME.getKey(), userBo.getUserName());
        if (userNameSismember){
            return "该用户名已被注册！";
        }

        Boolean phoneSismember = jedisPool.getResource().sismember(RedisKey.USER_PHONE.getKey(), userBo.getTelPhone());
        if (phoneSismember){
            return "该手机号已经注册！";
        }
        return null;
    }

}