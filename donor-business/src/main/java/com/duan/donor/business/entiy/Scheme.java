package com.duan.donor.business.entiy;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 实体类
 * </p>
 *
 * @author duanqiangwei
 * @since 2021-02-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Scheme implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    /**
     * 方案名称
     */
    private String schemeName;

    /**
     * 方案简介
     */
    private String schemeDescription;

    /**
     * 方案的价格
     */
    private double schemePrice;

    /**
     * 发布者
     */
    private Integer createUser;

    /**
     * 方案的封面
     */
    private String schemePicture;

    /**
     * 是否删除(0未删除，1已删除)
     */
    private Integer isDelete;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}