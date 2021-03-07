package com.duan.donor.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duan.donor.business.bo.UserBo;
import com.duan.donor.business.entiy.User;
import com.duan.donor.business.mapper.UserMapper;
import com.duan.donor.business.service.IUserService;
import com.duan.donor.business.vo.UserVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author duanqiangwei
 * @since 2021-02-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public PageInfo<UserVo> getAllUser(Integer page,Integer pageSize){

        //分页
        PageHelper.startPage(page, pageSize);

        //查询数据
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id","user_name","tel_phone","picture","email","gender"); //查询固定的字段
        List<User> allUserList = baseMapper.selectList(wrapper);

        //处理结果
        List<UserVo> list = new ArrayList<>();
        if (!allUserList.isEmpty()){
            for (User user : allUserList) {
                UserVo userVo = new UserVo();
                userVo.setUserId(user.getId());
                userVo.setUserName(user.getUserName());
                userVo.setEmail(user.getEmail());
                userVo.setGender(user.getGender());
                userVo.setTelPhone(user.getTelPhone());
                userVo.setPicture(user.getPicture());
                list.add(userVo);
            }
        }

        PageInfo<UserVo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public boolean createUser(User user) {
        return userMapper.createUser(user);
    }

    /**
     * 根据用户名查询用户信息
     * @param userName
     * @return
     */
    @Override
    public UserVo findByUserName(String userName) {
        UserVo userVo = new UserVo();
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id","user_name","tel_phone","picture","email","gender"); //查询固定的字段
        wrapper.eq("user_name",userName);
        User selectOne = baseMapper.selectOne(wrapper);
        if (selectOne != null){
            userVo.setUserId(selectOne.getId());
            userVo.setUserName(selectOne.getUserName());
            userVo.setPicture(selectOne.getPicture());
            userVo.setTelPhone(selectOne.getTelPhone());
            userVo.setGender(selectOne.getGender());
            userVo.setEmail(selectOne.getEmail());
        }

        return userVo;
    }

}