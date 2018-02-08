package com.it2go.employee.ui.jsf;

import com.it2go.masterdata.Continent;
import lombok.Data;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;

@Data
@FacesComponent(value = "countrySelect")
public class CountrySelectComponent extends UIInputComponent implements NamingContainer {

    private Continent continent;
    private UIInput continentSelect;
    private UIInput countrySelect;

    /**
     * Returns the component family of {@link UINamingContainer}.
     * (that's just required by composite component)
     */
    @Override
    public String getFamily() {
        return UINamingContainer.COMPONENT_FAMILY;
    }
}
