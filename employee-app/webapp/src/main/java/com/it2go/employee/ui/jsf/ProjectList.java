package com.it2go.employee.ui.jsf;

import com.it2go.employee.entities.Project;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIData;
import javax.faces.component.UINamingContainer;
import java.util.List;

@FacesComponent(value="com.it2go.employee.ui.jsf.ProjectList")
public class ProjectList extends UINamingContainer {

    private UIData table;

    /**
     * Returns the component family of {@link UINamingContainer}.
     * (that's just required by composite component)
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }

    public void add() {
        ((List) getAttributes().get("value")).add(new Project());
    }

    public void remove() {
        ((List) getAttributes().get("value")).remove(table.getRowData());
    }

    public UIData getTable() {
        return table;
    }

    public void setTable(UIData table) {
        this.table = table;
    }
}
