package com.duan.donor.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duan.donor.business.entiy.User;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author duanqiangwei
 * @since 2021-02-14
 */
public interface UserMapper extends BaseMapper<User> {

    boolean createUser(@Param("user") User user);
}