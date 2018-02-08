package com.it2go.framework.util.config;

import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j;
import org.apache.commons.configuration2.BaseConfiguration;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.JNDIConfiguration;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.naming.NoInitialContextException;

@UtilityClass
@Log4j
public class JndiConfigurationProducer {
    public Configuration getInstance() {
        try {
            final InitialContext initialContext = new InitialContext();
            try {
                log.debug("Testing for 'java:comp/env'");
                final Context tomcatContext = (Context) initialContext.lookup("java:comp/env");
                log.debug("'java:comp/env' found, using Tomcat kind of Jndi");
                return new JNDIConfiguration(tomcatContext);
            } catch (final NoInitialContextException e) {
                // no Jndi
                return new BaseConfiguration();
            } catch (final NamingException ne) {
                // Payara
                log.debug("NamingException => using Payara kind of Jndi");
                return new JNDIConfiguration(initialContext);
            }
        } catch (NamingException e) {
            log.error(null, e);
            throw new RuntimeException(e);
        }
    }
}