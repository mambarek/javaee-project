package com.it2go.employee.ui.controller;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@SessionScoped
public class WebFlowController implements Serializable{

    Map<String, Map<String, Object>> viewParamsCache = new HashMap<>();

    public void putViewParams(String viewId, Map<String, Object> paramsMap){
        final Map<String, Object> map = viewParamsCache.get(viewId);
        if(map != null)
            map.putAll(paramsMap);
        else
            viewParamsCache.put(viewId, paramsMap);
    }

    public Map<String, Object> getViewParams(String viewId){
        return viewParamsCache.get(viewId);
    }
}
