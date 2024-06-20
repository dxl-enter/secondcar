package com.dippy.es.vo;

import io.swagger.annotations.ApiModel;


@ApiModel(value = "", description = "")
public class AggChildren {
    private String value;
    private boolean active;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }


    @Override
    public String toString() {
        return "AggChildren{" +
                "value='" + value + '\'' +
                ", active=" + active +
                '}';
    }
}
