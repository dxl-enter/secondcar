package com.dippy.vo;

import com.dippy.entity.CarInfo;

import java.util.List;

public class CarInfoPageVo  {


    private List<CarInfo> carInfos;

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

    public List<CarInfo> getCarInfos() {
        return carInfos;
    }

    public void setCarInfos(List<CarInfo> carInfos) {
        this.carInfos = carInfos;
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
}
