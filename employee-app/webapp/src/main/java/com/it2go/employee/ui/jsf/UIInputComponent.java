package com.it2go.employee.ui.jsf;

import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UIInputComponent extends UIInput {
    private static final String PROPERTIES_EXT = ".properties";


    private static Logger LOGGER = Logger.getLogger("com.it2go.employee.ui.jsf",
            "javax.faces.LogStrings");

    private Map<String, String> resourceBundleMap = null;

    /**
     * Returns the component family of {@link UINamingContainer}.
     * (that's just required by composite component)
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    /*
    ** Fix für composit component Bundle können nicht gefunden werden
     */
    @Override
    public Map<String, String> getResourceBundleMap() {
        ResourceBundle resourceBundle = null;
        if (null == resourceBundleMap) {
            FacesContext context = FacesContext.getCurrentInstance();
            UIViewRoot root = context.getViewRoot();
            Locale currentLocale = null;
            if (null != context) {
                if (null != root) {
                    currentLocale = root.getLocale();
                }
            }
            if (null == currentLocale) {
                currentLocale = Locale.getDefault();
            }

            if (this.getAttributes().containsKey(Resource.COMPONENT_RESOURCE_KEY)) {
                Resource ccResource = (Resource)
                        this.getAttributes().get(Resource.COMPONENT_RESOURCE_KEY);
                if (null != ccResource) {
                    InputStream propertiesInputStream = null;
                    try {
                        propertiesInputStream = ccResource.getInputStream();
                        resourceBundle = findComponentResourceBundleLocaleMatch(ccResource.getResourceName(),
                                ccResource.getLibraryName(), currentLocale.getLanguage());
                    } catch (IOException ex) {
                        Logger.getLogger(UIComponent.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (null != propertiesInputStream) {
                            try {
                                propertiesInputStream.close();
                            } catch (IOException ioe) {
                                if (LOGGER.isLoggable(Level.SEVERE)) {
                                    LOGGER.log(Level.SEVERE, null, ioe);
                                }
                            }
                        }
                    }
                }
            }

            if (null != resourceBundle) {
                final ResourceBundle bundle = resourceBundle;
                resourceBundleMap =
                        new Map() {
                            // this is an immutable Map

                            public String toString() {
                                StringBuffer sb = new StringBuffer();
                                Iterator<Entry<String, Object>> entries =
                                        this.entrySet().iterator();
                                Map.Entry<String, Object> cur;
                                while (entries.hasNext()) {
                                    cur = entries.next();
                                    sb.append(cur.getKey()).append(": ").append(cur.getValue()).append('\n');
                                }

                                return sb.toString();
                            }

                            // Do not need to implement for immutable Map
                            public void clear() {
                                throw new UnsupportedOperationException();
                            }


                            public boolean containsKey(Object key) {
                                boolean result = false;
                                if (null != key) {
                                    result = (null != bundle.getObject(key.toString()));
                                }
                                return result;
                            }


                            public boolean containsValue(Object value) {
                                Enumeration<String> keys = bundle.getKeys();
                                boolean result = false;
                                while (keys.hasMoreElements()) {
                                    Object curObj = bundle.getObject(keys.nextElement());
                                    if ((curObj == value) ||
                                            ((null != curObj) && curObj.equals(value))) {
                                        result = true;
                                        break;
                                    }
                                }
                                return result;
                            }


                            public Set<Entry<String, Object>> entrySet() {
                                HashMap<String, Object> mappings = new HashMap<String, Object>();
                                Enumeration<String> keys = bundle.getKeys();
                                while (keys.hasMoreElements()) {
                                    String key = keys.nextElement();
                                    Object value = bundle.getObject(key);
                                    mappings.put(key, value);
                                }
                                return mappings.entrySet();
                            }


                            @Override
                            public boolean equals(Object obj) {
                                return !((obj == null) || !(obj instanceof Map))
                                        && entrySet().equals(((Map) obj).entrySet());

                            }


                            public Object get(Object key) {
                                if (null == key) {
                                    return null;
                                }
                                try {
                                    return bundle.getObject(key.toString());
                                } catch (MissingResourceException e) {
                                    return "???" + key + "???";
                                }
                            }


                            public int hashCode() {
                                return bundle.hashCode();
                            }


                            public boolean isEmpty() {
                                Enumeration<String> keys = bundle.getKeys();
                                return !keys.hasMoreElements();
                            }


                            public Set keySet() {
                                Set<String> keySet = new HashSet<String>();
                                Enumeration<String> keys = bundle.getKeys();
                                while (keys.hasMoreElements()) {
                                    keySet.add(keys.nextElement());
                                }
                                return keySet;
                            }


                            // Do not need to implement for immutable Map
                            public Object put(Object k, Object v) {
                                throw new UnsupportedOperationException();
                            }


                            // Do not need to implement for immutable Map
                            public void putAll(Map t) {
                                throw new UnsupportedOperationException();
                            }


                            // Do not need to implement for immutable Map
                            public Object remove(Object k) {
                                throw new UnsupportedOperationException();
                            }


                            public int size() {
                                int result = 0;
                                Enumeration<String> keys = bundle.getKeys();
                                while (keys.hasMoreElements()) {
                                    keys.nextElement();
                                    result++;
                                }
                                return result;
                            }


                            public java.util.Collection values() {
                                ArrayList<Object> result = new ArrayList<Object>();
                                Enumeration<String> keys = bundle.getKeys();
                                while (keys.hasMoreElements()) {
                                    result.add(
                                            bundle.getObject(keys.nextElement()));
                                }
                                return result;
                            }
                        };

            }

            if (null == resourceBundleMap) {
                resourceBundleMap = Collections.EMPTY_MAP;
            }
        }
        return resourceBundleMap;
    }

    private ResourceBundle findComponentResourceBundleLocaleMatch(String resourceName, String libraryName, String lng) {
        FacesContext context = FacesContext.getCurrentInstance();
        ResourceBundle resourceBundle = null;
        int i;
        if (-1 != (i = resourceName.lastIndexOf("."))) {
            if (null != context) {
                InputStream propertiesInputStream = null;
                try {
                    propertiesInputStream = getResourceInputStream(context, resourceName.substring(0, i), libraryName, lng);
                    resourceBundle = new PropertyResourceBundle(propertiesInputStream);
                } catch (IOException ex) {
                    Logger.getLogger(UIComponent.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (null != propertiesInputStream) {
                        try {
                            propertiesInputStream.close();
                        } catch (IOException ioe) {
                            if (LOGGER.isLoggable(Level.SEVERE)) {
                                LOGGER.log(Level.SEVERE, null, ioe);
                            }
                        }
                    }
                }
            }
        }
        return resourceBundle;
    }

    private InputStream getResourceInputStream(FacesContext context, final String resourceName, String libraryName, String lng) throws IOException {
        InputStream propertiesInputStream = null;
        propertiesInputStream = getPropertiesResourceInputStream(context, String.format("%s_%s%s", resourceName, lng, PROPERTIES_EXT), libraryName);
        if (null == propertiesInputStream) {
            propertiesInputStream = getPropertiesResourceInputStream(context, resourceName + PROPERTIES_EXT, libraryName);
        }
        return propertiesInputStream;
    }

    private InputStream getPropertiesResourceInputStream(FacesContext context, final String resourceName, String libraryName) throws IOException {
        Resource result = context.getApplication().getResourceHandler().createResource(resourceName, libraryName);
        if (null == result) {
            return null;
        }
        return result.getInputStream();
    }

}
