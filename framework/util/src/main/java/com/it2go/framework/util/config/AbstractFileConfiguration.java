package com.it2go.framework.util.config;

import lombok.Getter;
import lombok.NonNull;
import org.apache.commons.configuration2.CompositeConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.io.AbsoluteNameLocationStrategy;
import org.apache.commons.configuration2.io.ClasspathLocationStrategy;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractFileConfiguration implements Serializable {

    private static final long serialVersionUID = -8149560235275849064L;

    private static final String EXTERNAL_CONFIG_LOCATION_PARAMETER = "configFile";

    public enum Source {
        XML, PROPERTIES, BOTH
    }

    private static final String XML_FILE_PATTERN = "property/%s.xml";
    private static final String PROPERTIES_FILE_PATTERN = "property/%s.properties";
    private final String id;
    private final boolean useXml;
    private final boolean useProperties;

    private static final Configuration jndiConfiguration = JndiConfigurationProducer.getInstance();

    private static final Configuration systemConfiguration = SystemConfigurationProducer.getInstance();

    @Getter(lazy = true)
    private final Configuration configuration = getConfigurationInternal();

    protected AbstractFileConfiguration(final String id) {
        this(id, Source.XML);
    }

    protected AbstractFileConfiguration(@NonNull final String id, @NonNull final Source source) {
        final boolean useXml;
        final boolean useProperties;
        switch (source) {
            case XML:
                useXml = true;
                useProperties = false;
                break;
            case PROPERTIES:
                useXml = false;
                useProperties = true;
                break;
            case BOTH:
                useXml = true;
                useProperties = true;
                break;
            default:
                throw new RuntimeException();
        }
        this.id = id;
        this.useXml = useXml;
        this.useProperties = useProperties;
    }

    private Configuration getConfigurationInternal() {
        final CompositeConfiguration configuration = new CompositeConfiguration();
        configuration.setThrowExceptionOnMissing(true);

        configuration.addConfiguration(systemConfiguration.subset(id));
        final List<String> externalLocations = systemConfiguration.getList(String.class, EXTERNAL_CONFIG_LOCATION_PARAMETER);
        if (externalLocations != null) {
            for (final String location : externalLocations) {
                final FileBasedConfigurationBuilder<? extends FileBasedConfiguration> externalConfigurationBuilder;
                if (location.endsWith(".xml")) {
                    externalConfigurationBuilder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(XMLConfiguration.class);
                } else if (location.endsWith(".properties")) {
                    externalConfigurationBuilder = new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class);
                } else {
                    throw new IllegalArgumentException("Nur .xml und .properties werden unterstützt!");
                }
                try {
                    final Configuration externalConfig = externalConfigurationBuilder.configure(new Parameters().fileBased()
                            .setLocationStrategy(new AbsoluteNameLocationStrategy())
                            .setFileName(location)).getConfiguration();
                    configuration.addConfiguration(externalConfig.subset(id));

                } catch (final ConfigurationException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }
        configuration.addConfiguration(jndiConfiguration.subset(id));

        if (useXml) {
            try {
                configuration.addConfiguration(
                        new FileBasedConfigurationBuilder<>(XMLConfiguration.class)
                                .configure(
                                        new Parameters().fileBased()
                                                .setLocationStrategy(new ClasspathLocationStrategy())
                                                .setFileName(String.format(XML_FILE_PATTERN, id))).getConfiguration());
            } catch (final ConfigurationException e) {
                throw new RuntimeException(e);
            }
        }

        if (useProperties) {
            try {
                configuration.addConfiguration(
                        new FileBasedConfigurationBuilder<>(PropertiesConfiguration.class)
                                .configure(
                                        new Parameters().fileBased()
                                                .setLocationStrategy(new ClasspathLocationStrategy())
                                                .setFileName(String.format(PROPERTIES_FILE_PATTERN, id))).getConfiguration());
            } catch (final ConfigurationException e) {
                throw new RuntimeException(e);
            }
        }

        return configuration;
    }
}

