package com.it2go.framework.util;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author mmbarek
 *
 * Java Property is "ISO-8859-1" based, so to get another Encoding
 * you must convert the stream to your Encoding
 */
public class EncodedPropertyResourceBundle extends ResourceBundle {
    /**
     * Bundle with unicode data
     */
    protected ResourceBundle bundle;

    // default
    protected String encoding = "UTF-8";


    public EncodedPropertyResourceBundle(ResourceBundle bundle, String encoding) {
        super();
        this.bundle = bundle;
        this.encoding = encoding;
    }

    @Override
    protected Object handleGetObject(String key) {
        final String value = bundle.getString(key);
        if (value == null)
            return null;
        try {

            String encodedValue = new String(value.getBytes("ISO-8859-1"), encoding);

            //System.out.println("-- EncodedPropertyResourceBundle::handleGetObject value: " + value);
            //System.out.println("-- EncodedPropertyResourceBundle::handleGetObject encodedValue: " + encodedValue);

            return encodedValue;
        } catch (final UnsupportedEncodingException e) {
            throw new RuntimeException("Encoding not supported", e);
        }
    }

    @Override
    public Enumeration<String> getKeys() {
        // TODO Auto-generated method stub
        return bundle.getKeys();
    }

    public static EncodedPropertyResourceBundle getResourceBundle(String bundleName, Locale locale)
    {
        //File file = new File(path);

        //ClassLoader loader=null;
        // try {
        //URL[] urls = {file.toURI().toURL()};
        //loader = new URLClassLoader(urls);

        //ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale, loader);

        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);

        //System.out.println("## ResourceBundle test \u00C3\u00A4" + bundle.getString("view.common.validation.selectionInvalid"));
        //System.out.println("## ResourceBundle test \u00E4" + bundle.getString("view.tabs.customer.dealerFinancing.title"));

//             try {
//                 //String test1 = "\u00C3\u00A4";
//                 //String s1 = new String(test1.getBytes("ISO-8859-1"), "UTF-8");
//                 //System.out.println("## ResourceBundle test1 convert \u00C3\u00A4 : " + s1);
//
//                 //String test2 = "\u00E4";
//                 //String s2 = new String(test2.getBytes("ISO-8859-1"), "UTF-8");
//                 //System.out.println("## ResourceBundle test2 convert \u00E4 : " + s2);
//            } catch (UnsupportedEncodingException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }

        EncodedPropertyResourceBundle utf8Bundle = new EncodedPropertyResourceBundle(bundle, "UTF-8");

        return utf8Bundle;

        //        } catch (MalformedURLException ex) { }

        //        return null;
    }

}

