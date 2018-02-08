package com.it2go.framework.util.ldap;

import com.it2go.framework.util.config.AbstractFileConfiguration;
import lombok.Getter;

@Getter
public class LdapConfiguration extends AbstractFileConfiguration {

    @Getter
    private static final LdapConfiguration INSTANCE = new LdapConfiguration();

    private final String ldapURI = getConfiguration().getString("ldapURI");
    private final String ldapAdmin = getConfiguration().getString("ldapAdmin");
    private final String ldapAdminPassword = getConfiguration().getString("ldapAdminPassword");

    private LdapConfiguration() {
        super("ldap");
    }
}
