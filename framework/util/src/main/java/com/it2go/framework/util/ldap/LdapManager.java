package com.it2go.framework.util.ldap;

import sun.misc.BASE64Encoder;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Hashtable;

public class LdapManager {

    private final static String contextFactory = "com.sun.jndi.ldap.LdapCtxFactory";
    private static final int SALT_LENGTH = 4;

    private static DirContext ldapContext () throws Exception {
        Hashtable<String,String> env = new Hashtable <String,String>();
        return ldapContext(env);
    }

    private static DirContext ldapContext (Hashtable <String,String>env) throws Exception {
        final LdapConfiguration ldapConfig = LdapConfiguration.getINSTANCE();
        env.put(Context.INITIAL_CONTEXT_FACTORY, contextFactory);
        env.put(Context.PROVIDER_URL, ldapConfig.getLdapURI());
        DirContext ctx = new InitialDirContext(env);
        return ctx;
    }

    private static String getUid (String user) throws Exception {
        DirContext ctx = ldapContext();

        String filter = "(uid=" + user + ")";
        SearchControls ctrl = new SearchControls();
        ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
        NamingEnumeration answer = ctx.search("", filter, ctrl);

        String dn;
        if (answer.hasMore()) {
            SearchResult result = (SearchResult) answer.next();
            dn = result.getNameInNamespace();
        }
        else {
            dn = null;
        }
        answer.close();
        return dn;
    }

    private static boolean testBind (String dn, String password) throws Exception {
        Hashtable<String,String> env = new Hashtable <String,String>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, dn);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            ldapContext(env);
        }
        catch (javax.naming.AuthenticationException e) {
            return false;
        }
        return true;
    }

    private static DirContext getAdminContext (String dn, String password) throws Exception {
        Hashtable<String,String> env = new Hashtable <String,String>();
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, dn);
        env.put(Context.SECURITY_CREDENTIALS, password);

        try {
            return ldapContext(env);
        }
        catch (javax.naming.AuthenticationException e) {
            return null;
        }
    }

    public static String generateOpenLdapSSHA(byte[] password)
            throws NoSuchAlgorithmException {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        secureRandom.nextBytes(salt);

        MessageDigest crypt = MessageDigest.getInstance("SHA-1");
        //MessageDigest crypt = MessageDigest.getInstance("SHA");
        crypt.reset();
        crypt.update(password);
        crypt.update(salt);
        byte[] hash = crypt.digest();

        byte[] hashPlusSalt = new byte[hash.length + salt.length];
        System.arraycopy(hash, 0, hashPlusSalt, 0, hash.length);
        System.arraycopy(salt, 0, hashPlusSalt, hash.length, salt.length);

        return "{SSHA}" +
                Base64.getEncoder().encodeToString(hashPlusSalt);
    }

    // Apache directory (AD)
    public static String encodeADPassword(String password) throws NoSuchAlgorithmException {
        String algorithm = "SHA-1";

        // Calculate hash value
        MessageDigest md = MessageDigest.getInstance(algorithm);
        md.update(password.getBytes());
        byte[] bytes = md.digest();

        // Print out value in Base64 encoding
        BASE64Encoder base64encoder = new BASE64Encoder();
        String hash = base64encoder.encode(bytes);
        System.out.println("{SSHA}"+hash);

        return "{SSHA}"+ hash;
    }

    public static void setUserPassword(String user, String newPassword){
        final LdapConfiguration ldapConfig = LdapConfiguration.getINSTANCE();
        //.first check newPassword format
        // TODO boolean pwdOk = PasswordChecker.check(newPassword);

        String dn = null;
        try {
            dn = getUid( ldapConfig.getLdapAdmin() );
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dn != null) {
            DirContext adminContext = null;
            try {
                adminContext = getAdminContext(dn, ldapConfig.getLdapAdminPassword());

                final String pwdHash = encodeADPassword(newPassword);
                ModificationItem[] mods = new ModificationItem[1];
                mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", pwdHash));
                String userDN = getUid(user);

                // Perform the update
                assert adminContext != null;
                adminContext.modifyAttributes(userDN, mods);

                System.out.println("Reset Password for: " + user);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    assert adminContext != null;
                    adminContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

        }
        else {
            System.out.println( "Admin '" + ldapConfig.getLdapAdmin() + "' not found" );
        }
    }

    public static void changeUserPassword(String user, String oldPassword, String newPassword) throws Exception {
        String dn = getUid( user );

        if (dn != null) {
            /* Found user - test password */
            if ( testBind( dn, oldPassword ) ) {
                System.out.println( "user '" + user + "' authentication succeeded" );
                setUserPassword(user, newPassword);
            }
            else {
                System.out.println( "user '" + user + "' authentication failed" );
            }
        }
        else {
            System.out.println( "user '" + user + "' not found" );
        }
    }

    public static boolean authenticateUser(String user, String password) throws Exception {
        String dn = getUid( user );

        if (dn != null) {
            /* Found user - test password */
            if ( testBind( dn, password ) ) {
                System.out.println( "user '" + user + "' authentication succeeded" );
                return true;
            }
            else {
                System.out.println( "user '" + user + "' authentication failed" );
                return false;
            }
        }
        else {
            System.out.println( "user '" + user + "' not found" );
            return false;
        }
    }

    public static void main(String args[]) throws Exception {

        setUserPassword("mmbarek", "mmbarek");
    }

    public static void test0() throws Exception {
        String admin = "admin";
        String password = "secret";
        String user = "mmbarek";
        String dn = getUid( user );

        if (dn != null) {
            /* Found user - test password */
            if ( testBind( dn, password ) ) {
                System.out.println( "user '" + user + "' authentication succeeded" );
                System.exit(0);
            }
            else {
                System.out.println( "user '" + user + "' authentication failed" );
                System.exit(1);
            }
        }
        else {
            System.out.println( "user '" + user + "' not found" );
            System.exit(1);
        }
    }

    public static void test1() {
        String admin = "admin";
        String password = "secret";

        String dn = null;
        try {
            dn = getUid( admin );
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (dn != null) {
            DirContext adminContext = null;
            try {
                adminContext = getAdminContext(dn, password);
                //set password is a ldap modfy operation
                //ModificationItem[] mods = new ModificationItem[2];

                //Replace the "unicdodePwd" attribute with a new value
                //Password must be both Unicode and a quoted string
/*                String oldPassword = "secret";
                String oldQuotedPassword = "\"" + oldPassword + "\"";
                byte[] oldUnicodePassword = oldQuotedPassword.getBytes("UTF-16LE");
                String newPassword = "secret";
                String newQuotedPassword = "\"" + newPassword + "\"";
                byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");*/
                //final String generateSSHA = generateSSHA(newUnicodePassword);

        /*mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute("userPassword", oldPassword));
        mods[1] = new ModificationItem(DirContext.ADD_ATTRIBUTE, new BasicAttribute("userPassword", newPassword));*/
                final String pwdHash = encodeADPassword("password");
                ModificationItem[] mods = new ModificationItem[1];
                mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", pwdHash));
                String user = "mmbarek";
                String userDN = getUid(user);

                // Perform the update
                assert adminContext != null;
                adminContext.modifyAttributes(userDN, mods);
                System.out.println(user + " " + mods);

                System.out.println("Reset Password for: " + user);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    adminContext.close();
                } catch (NamingException e) {
                    e.printStackTrace();
                }
            }

        }
        else {
            System.out.println( "Admin '" + admin + "' not found" );
            System.exit(1);
        }

    }
}
