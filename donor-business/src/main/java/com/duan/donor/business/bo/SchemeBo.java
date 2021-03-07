package com.duan.donor.business.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(description="方案对象")
public class SchemeBo implements Serializable {

    /**
     * 方案的id
     */
    @ApiModelProperty(value="方案的id",name="schemeId")
    private Integer schemeId;

    /**
     * 方案名称
     */
    @ApiModelProperty(value="方案名称",name="schemeName")
    private String schemeName;

    /**
     * 方案价格
     */
    @ApiModelProperty(value="方案价格",name="schemePrice")
    private Double schemePrice;

    /**
     * 方案描述
     */
    @ApiModelProperty(value="方案描述",name="description")
    private String schemeDescription;

    /**
     * 方案封面
     */
    @ApiModelProperty(value="方案封面",name="schemePicture")
    private String schemePicture;
}