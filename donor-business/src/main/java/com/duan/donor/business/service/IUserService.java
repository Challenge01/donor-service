package com.duan.donor.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.duan.donor.business.bo.UserBo;
import com.duan.donor.business.entiy.User;
import com.duan.donor.business.vo.UserVo;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author duanqiangwei
 * @since 2021-02-14
 */
public interface IUserService extends IService<User> {

    PageInfo<UserVo> getAllUser(Integer page,Integer pageSize);

    boolean createUser(User user);

    UserVo findByUserName(String userName);

}