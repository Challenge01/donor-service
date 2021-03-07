package com.duan.donor.business.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.duan.donor.business.entiy.Scheme;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author duanqiangwei
 * @since 2021-02-15
 */
public interface ISchemeService extends IService<Scheme> {

    boolean insertBatch(List<Scheme> schemeList);
}