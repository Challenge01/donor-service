package com.duan.donor.business.entiy;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 实体类
 * </p>
 *
 * @author duanqiangwei
 * @since 2021-02-13
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户Id
     */
    private Integer id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户手机号
     */
    private String telPhone;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String picture;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 登录的token
     */
    private String loginToken;

    /**
     * 更新时间
     */
    private Date updateTime;


}