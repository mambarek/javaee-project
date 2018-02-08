package com.it2go.employee.ui.jsf;

import com.it2go.masterdata.Continent;
import com.it2go.masterdata.Country;
import lombok.Data;

import javax.faces.component.FacesComponent;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIInput;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public void encodeBegin(FacesContext context) throws IOException {

        final Object value = this.getAttributes().get("value");

        if(value != null){
            String countryCode = (String) value;
            if(!countryCode.isEmpty()){
                Continent continent = Continent.getContinentContainingCountry(countryCode);

                final Object continentSelectValue = continentSelect.getValue();
                if (continent != null && continentSelectValue == null)
                    continentSelect.setValue(continent.getCode());
            }
        }

        super.encodeBegin(context);
    }

    public List<Country> getCountries(){
        if(continentSelect.getValue() != null)
            return Objects.requireNonNull(Continent.getContinentWithCode((String) continentSelect.getValue()))
                    .getCountries(FacesContext.getCurrentInstance().getViewRoot().getLocale());

        return new ArrayList<>();
    }

    @Override
    public void resetValue() {
        super.resetValue();
        this.continentSelect.resetValue();
        this.countrySelect.resetValue();
    }
}
