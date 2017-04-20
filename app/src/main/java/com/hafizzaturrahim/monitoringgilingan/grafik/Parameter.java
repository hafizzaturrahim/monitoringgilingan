package com.hafizzaturrahim.monitoringgilingan.grafik;

/**
 * Created by PC-34 on 4/20/2017.
 */

public class Parameter {
    String name,value;

    public Parameter(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
