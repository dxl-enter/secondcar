package com.dippy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dippy.entity.CarInfo;
import com.dippy.entity.CarPicture;
import com.dippy.es.model.CarInfoModel;
import com.dippy.mapper.CarInfoMapper;
import com.dippy.service.CarInfoService;
import com.dippy.service.CarPictureService;
import com.dippy.vo.CarBrandVo;
import com.dippy.vo.CarDetailVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author dippy
 * @since 2021-02-08
 */
@Service
public class CarInfoServiceImpl extends ServiceImpl<CarInfoMapper, CarInfo> implements CarInfoService {

    @Autowired
    private CarInfoMapper carInfoMapper;

    @Autowired
    private CarInfoService carInfoService;

    // 汽车图片
    @Autowired
    private CarPictureService carPictureService;

    /**
     * 查找所有汽车品牌
     *
     * @return
     */
    @Override
    public List<CarBrandVo> getAllCarBrand() {
        return carInfoMapper.getAllCarBrand();
    }

    /**
     * 分页获取汽车信息
     * @return
     */
    @Override
    public IPage<CarInfo> getAllCarInfoByPage() {

        // 分页查询。传入开始页码加每页条数
        Page<CarInfo> page = new Page<>(1,5);
        // CarInfoModel carInfoModel = new CarInfoModel();

        return carInfoMapper.selectPage(page, null);
    }



    @Override
    public CarInfo selectOneCarInfo(QueryWrapper<CarInfo> wrapper) {

        return carInfoMapper.selectOne(wrapper);
    }

    /**
     * 上架
     *
     * @param carId
     * @return
     */
    @Override
    public CarInfoModel up(Integer carId) {

        if (carId == null) {
            log.error("上架的ID是空的");
            return null;
        }

        CarInfoModel carInfoModel = new CarInfoModel();

        // 查出。
        // carInfoService.getOneCarInfoAndURLById(carId);
        CarInfo carInfoById = carInfoService.getById(carId);
        carInfoById.setCarStatus(0);// 0-已上架；1-未上架；2-已下架

        // carInfoMapper.update()

        // 查出对应的汽车图片
        List<CarPicture> carPictures = carPictureService.list(new QueryWrapper<CarPicture>().eq("car_id", carId));

        carInfoModel.setCarPictures(carPictures);
        BeanUtil.copyProperties(carInfoById,carInfoModel);

        return carInfoModel;
    }

    @Override
    public List<CarInfo> getAllCarInfo() {
        // System.out.println("2222222 carInfoMapper  " + carInfoMapper.toString());
        // System.out.println("2222222 CarInfoServiceImpl  " + CarInfoServiceImpl.class.hashCode());

        return carInfoMapper.selectList(null);
    }

    @Override
    public IPage<CarInfo> findMySellCarByPage(Page<CarInfo> page, Integer userId) {
        IPage<CarInfo> carByPage = carInfoMapper.findMySellCarByPage(page, userId);
        return carByPage;
    }

    @Override
    public CarDetailVo getCarDetailByCarId(Integer carId) {

        CarDetailVo carDetailVo = carInfoMapper.getCarDetailByCarId(carId);

        return carDetailVo;
    }


}
