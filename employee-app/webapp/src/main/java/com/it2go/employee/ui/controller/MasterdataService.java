package com.it2go.employee.ui.controller;

import com.it2go.masterdata.Continent;
import com.it2go.masterdata.Country;
import com.it2go.masterdata.Gender;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

@Named
public class MasterdataService {

    public List<Gender> getGenders(){
        return Arrays.asList(Gender.values());
    }

    public List<Continent> getContinents() {
        return Arrays.asList(Continent.values());
    }

    public Continent getNullValueContinent(){

        return null;//Continent.AFRICA;
    }

    //public List<Country>
    public String getLocalizedNameFor(Object dataObject) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();

        if (dataObject instanceof Gender) {
            String resLabel;
            Gender gender = (Gender) dataObject;
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("com/it2go/masterdata/i18n/gender/gender", locale);
                resLabel = resourceBundle.getString(gender.toString());
            } catch (Exception e) {
                e.printStackTrace();
                resLabel = gender.getName();
            }

            return resLabel;
        }

        if (dataObject instanceof Continent) {
            String resLabel;
            Continent continent = (Continent) dataObject;
            try {
                ResourceBundle resourceBundle = ResourceBundle.getBundle("com/it2go/masterdata/i18n/continent/continent", locale);
                resLabel = resourceBundle.getString(continent.toString());
            } catch (Exception e) {
                e.printStackTrace();
                resLabel = continent.getName();
            }

            return resLabel;
        }

        if (dataObject instanceof Country) {

            Country country = (Country) dataObject;
            Locale l = new Locale("",country.getCode());
            return l.getDisplayCountry(locale);
        }

        return "";
    }


    public List<Country> getAllCountries(Locale locale){
        return Country.getAllCountries(locale);
    }
}
