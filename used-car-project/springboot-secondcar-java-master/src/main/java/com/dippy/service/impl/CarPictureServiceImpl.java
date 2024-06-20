package com.dippy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.CarPicture;
import com.dippy.mapper.CarPictureMapper;
import com.dippy.service.CarPictureService;
import com.dippy.util.PageUtils;
import com.dippy.util.Query;
import org.springframework.stereotype.Service;

import java.util.Map;



@Service("carPictureService")
public class CarPictureServiceImpl extends ServiceImpl<CarPictureMapper, CarPicture> implements CarPictureService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CarPicture> page = this.page(
                new Query<CarPicture>().getPage(params),
                new QueryWrapper<CarPicture>()
        );

        return new PageUtils(page);
    }

}
