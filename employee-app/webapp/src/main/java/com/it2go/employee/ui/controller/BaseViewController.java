package com.it2go.employee.ui.controller;

import java.io.Serializable;

public interface BaseViewController extends Serializable{

    String getViewId();
    String getPage();
}
