package com.it2go.employee.ui.jsf;

import lombok.Data;

@Data
public class SelectItemWrapper<T> {
    private T value;
    private String localizedLabel;

    public SelectItemWrapper(T value) {
        this.value = value;
    }

}
