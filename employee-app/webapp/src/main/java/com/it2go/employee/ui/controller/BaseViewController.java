package com.it2go.employee.ui.controller;

import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import java.io.Serializable;
import java.util.Iterator;

public interface BaseViewController extends Serializable{

    String getViewId();
    String getPage();

    default boolean hasError(String id, String parentId)
    {
        FacesContext ctx = FacesContext.getCurrentInstance();
        Iterator<String> msgs = ctx.getClientIdsWithMessages();

        String resolvedClientId = resolveClientId(id, parentId);
        if (resolvedClientId != null)
        {
            while (msgs.hasNext())
            {
                String clientId = (String) msgs.next();
                if (clientId != null && clientId.equals(resolvedClientId))
                {
                    return true;
                }
            }
        }

        return false;
    }

    public static String resolveClientId(String id, String parentId)
    {
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent component = findComponent(context, id, parentId);
        return component != null ? component.getClientId(context) : null;
    }

    public static UIComponent findComponent(FacesContext context, String id, String parentId)
    {
        if (id != null)
        {
            if (context != null)
            {
                UIViewRoot root = context.getViewRoot();

                if (root != null)
                {
                    UIComponent component = findComponentFor(context, root, id, parentId);

                    if (component != null)
                    {
                        return component;
                    }
                }
            }
        }

        return null;
    }

    public static UIComponent findComponentFor(FacesContext context, UIComponent component, String id, String parentId)
    {

        if (id == null || id.length() == 0)
        {
            return null;
        }

        UIComponent target = null;
        UIComponent parent = component;
        UIComponent root = component;

        // Nach oben
        while (null == target && null != parent)
        {
            target = parent.findComponent(id);
            root = parent;
            parent = parent.getParent();

            if (parentId != null && target != null && !target.getClientId(context).contains(parentId))
            {
                target = null;
            }
        }

        if (null == target)
        {
            // Nach unten
            target = findUIComponentNachUnten(context, root, id, parentId);
        }
        return target;
    }

    public static UIComponent findUIComponentNachUnten(FacesContext context, UIComponent root, String id, String parentId)
    {
        UIComponent target = null;
        for (Iterator<UIComponent> iter = root.getFacetsAndChildren(); iter.hasNext();)
        {
            UIComponent child = (UIComponent) iter.next();
            if (child instanceof NamingContainer)
            {
                try
                {
                    target = child.findComponent(id);
                }
                catch (IllegalArgumentException iae)
                {
                    continue;
                }
            }
            if (target == null)
            {
                if (child.getChildCount() > 0 || child.getFacetCount() > 0)
                {
                    target = findUIComponentNachUnten(context, child, id, parentId);
                }
            }

            if (target != null)
            {
                if (parentId != null && !target.getClientId(context).contains(parentId))
                {
                    target = null;
                }
                else
                {
                    break;
                }
            }

        }
        return target;
    }
}
