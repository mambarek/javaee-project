package com.it2go.framework.util.config;

import lombok.experimental.UtilityClass;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.SystemConfiguration;
import org.apache.commons.configuration2.convert.DefaultListDelimiterHandler;

@UtilityClass
public class SystemConfigurationProducer {
    public Configuration getInstance() {
        final SystemConfiguration systemConfiguration = new SystemConfiguration();
        systemConfiguration.setListDelimiterHandler(new DefaultListDelimiterHandler(','));
        return systemConfiguration;
    }
}
