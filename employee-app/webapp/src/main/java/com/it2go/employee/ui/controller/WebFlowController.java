package com.it2go.employee.ui.controller;

import com.it2go.framework.util.StringUtils;
import sun.util.locale.LocaleUtils;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Named
@SessionScoped
public class WebFlowController implements Serializable{

    private String locale = "de";

    /** Verfügbare Sprachen **/
    private static Map<String, Locale> availableLocales;
    static
    {
        availableLocales = new LinkedHashMap<String, Locale>();
        availableLocales.put("en", Locale.ENGLISH);
        availableLocales.put("de", Locale.GERMAN);
    }

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

    public void changeLocal(String newLocale, String outcome) throws IOException {

        locale = newLocale;
        if(!StringUtils.exists(locale))
            locale = "de";

        //return outcome+"?faces-redirect=true";
        FacesContext facesContext = FacesContext.getCurrentInstance();

        for (Map.Entry<String, Locale> entry : availableLocales.entrySet())
        {
            if (entry.getValue().toString().equals(newLocale))
                facesContext.getViewRoot().setLocale(entry.getValue());
        }

        ExternalContext ec = facesContext.getExternalContext();
        ec.redirect(((HttpServletRequest)ec.getRequest()).getRequestURI());
    }
}
