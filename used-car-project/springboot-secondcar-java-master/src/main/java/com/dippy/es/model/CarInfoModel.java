package com.dippy.es.model;


import com.dippy.entity.CarPicture;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/*
{
  "car-info" : {
    "mappings" : {
      "properties" : {
        "_class" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "carBrand" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "carCarMileage" : {
          "type" : "long"
        },
        "carColor" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "carConfiguration" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "carCount" : {
          "type" : "long"
        },
        "carDescribe" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "carDisplacement" : {
          "type" : "float"
        },
        "carFrontTyre" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "carFuelType" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "carGearBox" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "carId" : {
          "type" : "long"
        },
        "carPictureId" : {
          "type" : "long"
        },
        "carPictures" : {
          "properties" : {
            "carId" : {
              "type" : "long"
            },
            "carPictureId" : {
              "type" : "long"
            },
            "carPictureLocation" : {
              "type" : "long"
            },
            "carUrl" : {
              "type" : "text",
              "fields" : {
                "keyword" : {
                  "type" : "keyword",
                  "ignore_above" : 256
                }
              }
            }
          }
        },
        "carPrice" : {
          "type" : "float"
        },
        "carQualityTime" : {
          "type" : "long"
        },
        "carRegionId" : {
          "type" : "long"
        },
        "carSeat" : {
          "type" : "long"
        },
        "carSeries" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "carType" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "carUserTime" : {
          "type" : "long"
        },
        "createTime" : {
          "type" : "date"
        },
        "produceTime" : {
          "type" : "date"
        },
        "rearTyre" : {
          "type" : "text",
          "fields" : {
            "keyword" : {
              "type" : "keyword",
              "ignore_above" : 256
            }
          }
        },
        "transferCount" : {
          "type" : "long"
        },
        "updateTime" : {
          "type" : "date"
        }
      }
    }
  }
}



*/



@Document(indexName = "car-info", createIndex = true)
public class CarInfoModel implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 车辆id
     */
    @Id
    private Integer carId;

    /**
     * 汽车品牌
     */
    @Field(type = FieldType.Keyword, searchAnalyzer="ik_smart", analyzer = "ik_max_word")
    private String carBrand;

    /**
     * 车系
     */
    @Field(type = FieldType.Keyword, searchAnalyzer="ik_smart", analyzer = "ik_max_word")
    private String carSeries;

    /**
     * 车型（suv、小型车。。。）
     */
    @Field(type = FieldType.Keyword, searchAnalyzer="ik_smart", analyzer = "ik_max_word")
    private String carType;

    /**
     * 价格
     */
    @Field(type = FieldType.Double)
    private Double carPrice;

    /**
     * 汽车数量
     */
    @Field(type = FieldType.Integer)
    private Integer carCount;

    /**
     * 使用年限（单位/年）
     */
    @Field(type = FieldType.Integer)
    private Integer carUserTime;

    /**
     * 过户次数
     */
    @Field(type = FieldType.Integer)
    private Integer transferCount;

    /**
     * 里程（万公里）
     */
    @Field(type = FieldType.Double)
    private Double carCarMileage;

    /**
     * 汽车颜色
     */
    @Field(type = FieldType.Keyword)
    private String carColor;

    /**
     * 汽车图片地址
     */
    @Field(type = FieldType.Integer)
    private Integer carPictureId;

    /**
     * 变数箱（手动、自动、不限）
     */
    @Field(type = FieldType.Keyword)
    private String carGearBox;

    /**
     * 排量
     */
    @Field(type = FieldType.Double)
    private Double carDisplacement;

    /**
     * 汽车生产日期
     */
    @Field(type = FieldType.Date,format = DateFormat.date)
    private LocalDate produceTime;

    /**
     * 配置（天窗、GPS、真皮坐垫）
     */
    @Field(type = FieldType.Keyword)
    private String carConfiguration;

    /**
     * 座位数
     */
    @Field(type = FieldType.Integer)
    private Integer carSeat;

    /**
     * 燃料类型（柴油、机油、电力。。。）
     */
    @Field(type = FieldType.Keyword)
    private String carFuelType;

    /**
     * 质保时间（单位/年）
     */
    @Field(type = FieldType.Integer)
    private Integer carQualityTime;

    /**
     * 国别
     */
    @Field(type = FieldType.Integer)
    private Integer carRegionId;

    /**
     * 前轮胎尺寸
     */
    @Field(type = FieldType.Keyword)
    private String carFrontTyre;

    /**
     * 后轮胎尺寸
     */
    @Field(type = FieldType.Keyword)
    private String rearTyre;

    /**
     * 其他描述
     */
    @Field(type = FieldType.Keyword)
    private String carDescribe;

    /**
     * 创建时间
     */
    // @Field(type = FieldType.Date,format = DateFormat.date_hour_minute_second)

    // @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")  // 指定存储格式
    // @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")  // 数据格式转换，并加上8小时进行存储
    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    // @Field(type = FieldType.Date,format = DateFormat.date_hour_minute_second)

    // @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    // @JsonSerialize(using = LocalDateTimeSerializer.class)

    // @Field(type = FieldType.Date, format = DateFormat.custom,pattern ="yyyy-MM-dd HH:mm:ss")  // 指定存储格式
    // @JsonFormat(shape =JsonFormat.Shape.STRING,pattern ="yyyy-MM-dd HH:mm:ss",timezone ="GMT+8")  // 数据格式转换，并加上8小时进行存储

    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    /**
     * 汽车全部图片
     */
    @Field(type = FieldType.Nested)
    private List<CarPicture> carPictures;

    /**
     * 汽车标题
     */
    @Field(type = FieldType.Keyword, searchAnalyzer="ik_smart", analyzer = "ik_max_word")
    private String carTitle;

    /**
     * 汽车主图
     */
    @Field(type = FieldType.Keyword)
    private String carPicture;


    /**
     * 汽车状态
     */
    @Field(type = FieldType.Integer)
    private Integer carStatus;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarSeries() {
        return carSeries;
    }

    public void setCarSeries(String carSeries) {
        this.carSeries = carSeries;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public Double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(Double carPrice) {
        this.carPrice = carPrice;
    }

    public Integer getCarCount() {
        return carCount;
    }

    public void setCarCount(Integer carCount) {
        this.carCount = carCount;
    }

    public Integer getCarUserTime() {
        return carUserTime;
    }

    public void setCarUserTime(Integer carUserTime) {
        this.carUserTime = carUserTime;
    }

    public Integer getTransferCount() {
        return transferCount;
    }

    public void setTransferCount(Integer transferCount) {
        this.transferCount = transferCount;
    }

    public Double getCarCarMileage() {
        return carCarMileage;
    }

    public void setCarCarMileage(Double carCarMileage) {
        this.carCarMileage = carCarMileage;
    }

    public String getCarColor() {
        return carColor;
    }

    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public Integer getCarPictureId() {
        return carPictureId;
    }

    public void setCarPictureId(Integer carPictureId) {
        this.carPictureId = carPictureId;
    }

    public String getCarGearBox() {
        return carGearBox;
    }

    public void setCarGearBox(String carGearBox) {
        this.carGearBox = carGearBox;
    }

    public Double getCarDisplacement() {
        return carDisplacement;
    }

    public void setCarDisplacement(Double carDisplacement) {
        this.carDisplacement = carDisplacement;
    }

    public LocalDate getProduceTime() {
        return produceTime;
    }

    public void setProduceTime(LocalDate produceTime) {
        this.produceTime = produceTime;
    }

    public String getCarConfiguration() {
        return carConfiguration;
    }

    public void setCarConfiguration(String carConfiguration) {
        this.carConfiguration = carConfiguration;
    }

    public Integer getCarSeat() {
        return carSeat;
    }

    public void setCarSeat(Integer carSeat) {
        this.carSeat = carSeat;
    }

    public String getCarFuelType() {
        return carFuelType;
    }

    public void setCarFuelType(String carFuelType) {
        this.carFuelType = carFuelType;
    }

    public Integer getCarQualityTime() {
        return carQualityTime;
    }

    public void setCarQualityTime(Integer carQualityTime) {
        this.carQualityTime = carQualityTime;
    }

    public Integer getCarRegionId() {
        return carRegionId;
    }

    public void setCarRegionId(Integer carRegionId) {
        this.carRegionId = carRegionId;
    }

    public String getCarFrontTyre() {
        return carFrontTyre;
    }

    public void setCarFrontTyre(String carFrontTyre) {
        this.carFrontTyre = carFrontTyre;
    }

    public String getRearTyre() {
        return rearTyre;
    }

    public void setRearTyre(String rearTyre) {
        this.rearTyre = rearTyre;
    }

    public String getCarDescribe() {
        return carDescribe;
    }

    public void setCarDescribe(String carDescribe) {
        this.carDescribe = carDescribe;
    }

    public List<CarPicture> getCarPictures() {
        return carPictures;
    }

    public void setCarPictures(List<CarPicture> carPictures) {
        this.carPictures = carPictures;
    }

    public String getCarTitle() {
        return carTitle;
    }

    public void setCarTitle(String carTitle) {
        this.carTitle = carTitle;
    }

    public String getCarPicture() {
        return carPicture;
    }

    public void setCarPicture(String carPicture) {
        this.carPicture = carPicture;
    }

    public Integer getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(Integer carStatus) {
        this.carStatus = carStatus;
    }

    @Override
    public String toString() {
        return "CarInfoModel{" +
                "carId=" + carId +
                ", carBrand='" + carBrand + '\'' +
                ", carSeries='" + carSeries + '\'' +
                ", carType='" + carType + '\'' +
                ", carPrice=" + carPrice +
                ", carCount=" + carCount +
                ", carUserTime=" + carUserTime +
                ", transferCount=" + transferCount +
                ", carCarMileage=" + carCarMileage +
                ", carColor='" + carColor + '\'' +
                ", carPictureId=" + carPictureId +
                ", carGearBox='" + carGearBox + '\'' +
                ", carDisplacement=" + carDisplacement +
                ", produceTime=" + produceTime +
                ", carConfiguration='" + carConfiguration + '\'' +
                ", carSeat=" + carSeat +
                ", carFuelType='" + carFuelType + '\'' +
                ", carQualityTime=" + carQualityTime +
                ", carRegionId=" + carRegionId +
                ", carFrontTyre='" + carFrontTyre + '\'' +
                ", rearTyre='" + rearTyre + '\'' +
                ", carDescribe='" + carDescribe + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", carPictures=" + carPictures +
                ", carTitle='" + carTitle + '\'' +
                ", carPicture='" + carPicture + '\'' +
                ", carStatus=" + carStatus +
                '}';
    }
}
