package com.duan.donor.business.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserVo {


    /**
     * 用户id
     */
    @ApiModelProperty(value="用户id",name="userId")
    private Integer userId;

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
