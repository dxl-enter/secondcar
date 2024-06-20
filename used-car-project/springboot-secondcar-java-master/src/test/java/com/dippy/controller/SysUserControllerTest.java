package com.dippy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dippy.entity.CarInfo;
import com.dippy.entity.CarPicture;
import com.dippy.entity.SellerCar;
import com.dippy.service.CarInfoService;
import com.dippy.service.CarPictureService;
import com.dippy.service.SellerCarService;
import com.dippy.util.OrderUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class SysUserControllerTest {

    @Autowired
    public SellerCarService sellerCarService;

    @Autowired
    public CarPictureService carPictureService;

    @Autowired
    public CarInfoService carInfoService;



    @Test
    public void test1() {

        List<SellerCar> user_id = sellerCarService.list(new QueryWrapper<SellerCar>().eq("user_id", 1));

        user_id.stream().forEach(System.out::println);
    }

    @Test
    public void test2() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encode = encoder.encode("123456");
        System.out.println(encode);

        carPictureService.remove(new QueryWrapper<CarPicture>().eq("car_id", 45));
    }


    @Test
    public void test3() {
        // 2.1 分页查出该用户对应的所有汽车id
        Page<SellerCar> sellerCarPage = new Page<>(1,5);
        Page<SellerCar> sellerCarIdPage = sellerCarService.page(sellerCarPage,
                new QueryWrapper<SellerCar>().eq("user_id", 1).select("car_id"));

        List<SellerCar> sellerCarList = sellerCarIdPage.getRecords();

        List<Integer> carIdList = new ArrayList<>();
        sellerCarList.forEach(sellerCar -> {
            carIdList.add(sellerCar.getCarId());
        });
        List<CarInfo> carInfos1 = carInfoService.listByIds(carIdList);

        carInfos1.forEach(System.out::println);

    }

    @Test
    public void test4 () {
        System.out.println(OrderUtils.createOrderNumber(1));
    }


}
