package com.dippy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.SellerCar;
import com.dippy.mapper.SellerCarMapper;
import com.dippy.service.SellerCarService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service/*("sellerCarService")*/
public class SellerCarServiceImpl extends ServiceImpl<SellerCarMapper, SellerCar> implements SellerCarService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<SellerCar> page = this.page(
                new Query<SellerCar>().getPage(params),
                new QueryWrapper<SellerCar>()
        );

        return new PageUtils(page);
    }

}
