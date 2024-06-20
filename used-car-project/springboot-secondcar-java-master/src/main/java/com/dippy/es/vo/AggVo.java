package com.dippy.es.vo;


import io.swagger.annotations.ApiModel;

import java.util.List;


@ApiModel(value = "", description = "")
public class AggVo {
    private List<AggVoList> aggVoListList;

    @Override
    public String toString() {
        return "AggVo{" +
                "aggVoListList=" + aggVoListList +
                '}';
    }

    public List<AggVoList> getAggVoListList() {
        return aggVoListList;
    }

    public void setAggVoListList(List<AggVoList> aggVoListList) {
        this.aggVoListList = aggVoListList;
    }
}
