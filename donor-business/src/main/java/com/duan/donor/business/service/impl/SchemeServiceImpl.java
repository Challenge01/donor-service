package com.duan.donor.business.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.duan.donor.business.entiy.Scheme;
import com.duan.donor.business.mapper.SchemeMapper;
import com.duan.donor.business.service.ISchemeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author duanqiangwei
 * @since 2021-02-15
 */
@Service
public class SchemeServiceImpl extends ServiceImpl<SchemeMapper, Scheme> implements ISchemeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean insertBatch(List<Scheme> schemeList) {
        return baseMapper.insertBatch(schemeList);
    }
}