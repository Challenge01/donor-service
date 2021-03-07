package com.duan.donor.business.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description="用户对象")
public class UserBo implements Serializable {

    /**
     * 用户名
     */
    @ApiModelProperty(value="用户名",name="userName")
    private String userName;

    /**
     * 用户手机号
     */
    @ApiModelProperty(value="用户手机号",name="telPhone")
    private String telPhone;

    /**
     * 密码
     */
    @ApiModelProperty(value="用户密码",name="password")
    private String password;

    /**
     * 用户头像
     */
    @ApiModelProperty(value="用户头像",name="picture")
    private String picture;

    /**
     * 用户邮箱
     */
    @ApiModelProperty(value="用户邮箱",name="email")
    private String email;

    /**
     * 用户性别
     */
    @ApiModelProperty(value="用户性别",name="gender")
    private Integer gender;

}