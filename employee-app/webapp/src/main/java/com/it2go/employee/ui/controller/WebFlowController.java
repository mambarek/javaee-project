package com.it2go.employee.ui.controller;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Named
@SessionScoped
public class WebFlowController implements Serializable{

    private String locale = "en";

    private Map<String, Map<String, Object>> viewParamsCache = new ConcurrentHashMap<>();

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

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public void changeLocal(String newLocal, String outcome) throws IOException {

        locale = newLocal;
        //return outcome+"?faces-redirect=true";
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext ec = facesContext.getExternalContext();
        ec.redirect(((HttpServletRequest)ec.getRequest()).getRequestURI());
    }
}
