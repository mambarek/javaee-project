package com.it2go.employee.ui.jsf;

import com.it2go.masterdata.Continent;
import lombok.Data;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;

@Data
@FacesComponent(value = "countrySelect")
public class CountrySelectComponent extends UIInputComponent implements NamingContainer {

    private Continent continent;
    private UIInput continentSelect;
    private UIInput countrySelect;

}
