package com.dippy.es.vo;


import io.swagger.annotations.ApiModel;

import java.util.List;

@ApiModel(value = "", description = "")
public class AggVoList {
    private String title;
    private List<AggChildren> aggChildrenList;

    @Override
    public String toString() {
        return "AggVoList{" +
                "title='" + title + '\'' +
                ", aggChildrenList=" + aggChildrenList +
                '}';
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AggChildren> getAggChildrenList() {
        return aggChildrenList;
    }

    public void setAggChildrenList(List<AggChildren> aggChildrenList) {
        this.aggChildrenList = aggChildrenList;
    }
}
