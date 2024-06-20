package com.dippy.vo;

import com.dippy.entity.CarInfo;
import io.swagger.annotations.Api;

/**
 * 新增汽车信息的Vo
 */
@Api(value = "新增汽车VO")
public class AddCarVo extends CarInfo {


    // /**
    //  * 汽车主图
    //  */
    // @ApiModelProperty(value = "汽车主图")
    // private String carPicture;


    // /**
    //  * 汽车图片URL
    //  */
    // private String carUrl;
    //
    // /**
    //  * 汽车图片位置，0-显示主图；1-内部；2-外部；3-其他；4 轮播
    //  */
    // private Integer carPictureLocation;


}
