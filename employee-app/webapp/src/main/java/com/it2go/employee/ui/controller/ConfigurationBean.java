package com.it2go.employee.ui.controller;

/**
 * Created by xinyuan.zhang on 9/4/17.
 * FIX FOR CDI TO NULL RESOLVED BEAN
 * https://github.com/javaserverfaces/mojarra/issues/4264
 */
import static javax.faces.annotation.FacesConfig.Version.JSF_2_3;

import javax.faces.annotation.FacesConfig;

@FacesConfig(
        // Activates CDI build-in beans
        version = JSF_2_3
)
public class ConfigurationBean {
}
