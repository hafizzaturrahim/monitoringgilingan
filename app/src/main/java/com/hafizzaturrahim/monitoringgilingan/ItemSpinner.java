package com.hafizzaturrahim.monitoringgilingan;

/**
 * Created by PC-34 on 4/20/2017.
 */

public class ItemSpinner {
    String name,value;

    public ItemSpinner(String name, String value) {
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
