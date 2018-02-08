package com.it2go.employee.ui.controller;

import lombok.Data;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
@Data
public class GridController implements Serializable {

    private int pageNum = 0;
    private int pageSize = 10;

}
