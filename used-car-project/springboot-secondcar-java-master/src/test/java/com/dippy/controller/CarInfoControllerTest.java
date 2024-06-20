package com.dippy.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dippy.entity.CarInfo;
import com.dippy.entity.CarPicture;
import com.dippy.service.CarInfoService;
import com.dippy.service.CarPictureService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


@SpringBootTest
class CarInfoControllerTest {

    @Autowired
    public CarInfoService carInfoService;

    @Autowired
    public CarPictureService carPictureService;

    @Test
    public void test() {
        // 1. 查询汽车信息
        IPage<CarInfo> allCarInfo = carInfoService.getAllCarInfoByPage();
        List<CarInfo> carInfoList = allCarInfo.getRecords();
        List<Integer> carPictures = carInfoList.stream().map(CarInfo::getCarId).collect(Collectors.toList());
        System.out.println(carPictureService.list(new QueryWrapper<CarPicture>().eq("car_id", carPictures)));
    }

    @Test
    public void myPageQueryTest2() {
        CarPicture carPicture = new CarPicture();
        carPicture.setCarId(1);
        carPicture.setCarPictureLocation(15);
        carPicture.setCarUrl("111111111");

        carPictureService.saveOrUpdate(carPicture,
                new QueryWrapper<CarPicture>().eq("car_id", carPicture.getCarId())
                        .eq("car_picture_location", carPicture.getCarPictureLocation())
        );
    }


    @Test
    void contextLoads1() {

        List<School> list = new ArrayList<School>();
        list.add(new School(1, "张三丰", 0));
        list.add(new School(2, "张无忌", 1));
        list.add(new School(3, "张翠山", 2));
        list.add(new School(4, "殷梨亭", 3));
        list.add(new School(5, "俞莲舟", 3));
        list.add(new School(6, "宋远桥", 4));
        list.add(new School(7, "宋远桥", 5));

        List<Integer> idList = new ArrayList<>();
        //以id为6的对象为例,他的父节点是4,先将本身的id设置进列表中
        idList.add(6);

        List<Integer> integerList = getpid(idList, 4, list);

        System.out.println(integerList);


    }

    public List<Integer> getpid(List<Integer> idList, Integer pid, List<School> schools) {
        School school = schools.stream().filter(s -> Objects.equals(s.getId(), pid)).findFirst().get();
        idList.add(school.getId());
        if (school.getId() != 1) {
            getpid(idList, school.getPid(), schools);
        }

        return idList;
    }
}

class School {

    private Integer id;
    private String name;
    private Integer pid;

    public School(Integer id, String name, Integer pid) {
        this.id = id;
        this.name = name;
        this.pid = pid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }
}
