package com.duan.donor.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.duan.donor.business.entiy.Scheme;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author duanqiangwei
 * @since 2021-02-15
 */
public interface SchemeMapper extends BaseMapper<Scheme> {

    boolean insertBatch(@Param("schemeList") List<Scheme> schemeList);
}