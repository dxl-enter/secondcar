package com.dippy.es.vo;

import com.dippy.es.model.CarInfoModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;


@ApiModel(value = "ES查询后返回的结果", description = "ES查询后返回的结果VO")
public class SearchResult {

    /**
     * 查询到的所有商品
     */
    @ApiModelProperty(value = "ES查询到的所有商品")
    private List<CarInfoModel> CarInfoModel;

    /*=====================聚合分析=====================*/

    @ApiModelProperty(value = "汽车信息聚合")
    private CarAggVo carAggVoList;
    @ApiModelProperty(value = "聚合信息涉及的标题")
    private List<String> arrTitleList;

    private AggVo aggVo;


    /*=====================聚合分析=====================*/


    /*=====================以下分页属性=====================*/
    /**
     * 总共几页
     */
    private Long totalPage;

    /**
     * 当前页码
     */
    private Integer currPage;

    /**
     * 每页显示几个
     */
    private Integer pageSize;

    /**
     * 总记录数
     */
    private Long totalCount;
    /*=====================以上分页属性=====================*/



    public List<com.dippy.es.model.CarInfoModel> getCarInfoModel() {
        return CarInfoModel;
    }

    public void setCarInfoModel(List<com.dippy.es.model.CarInfoModel> carInfoModel) {
        CarInfoModel = carInfoModel;
    }

    public CarAggVo getCarAggVoList() {
        return carAggVoList;
    }

    public void setCarAggVoList(CarAggVo carAggVoList) {
        this.carAggVoList = carAggVoList;
    }

    public List<String> getArrTitleList() {
        return arrTitleList;
    }

    public void setArrTitleList(List<String> arrTitleList) {
        this.arrTitleList = arrTitleList;
    }

    public AggVo getAggVo() {
        return aggVo;
    }

    public void setAggVo(AggVo aggVo) {
        this.aggVo = aggVo;
    }

    public Long getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Long totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "CarInfoModel=" + CarInfoModel +
                ", carAggVoList=" + carAggVoList +
                ", arrTitleList=" + arrTitleList +
                ", aggVo=" + aggVo +
                ", totalPage=" + totalPage +
                ", currPage=" + currPage +
                ", pageSize=" + pageSize +
                ", totalCount=" + totalCount +
                '}';
    }
}
