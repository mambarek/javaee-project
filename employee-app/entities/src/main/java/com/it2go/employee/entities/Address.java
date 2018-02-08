package com.it2go.employee.entities;

import com.it2go.masterdata.Country;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Getter
@Setter
@Table(name = "ADDRESS")
@XmlRootElement
public class Address extends DomainEntity {

    @Basic
    @Column(name = "STREET_ONE", nullable = false)
    @Size(min = 3, max = 100)
    @NotNull
    private String streetOne;

    @Basic
    @Column(name = "BUILDING_NR", nullable = false)
    @Size(min = 1, max = 5)
    @NotNull
    private String buildingNr;

    @Basic
    @Column(name = "STREET_TWO", nullable = true)
    @Size(min = 3, max = 100)
    private String streetTwo;

    @Basic
    @Column(name = "ZIP_CODE", nullable = false)
    @Size(min = 5, max = 5)
    @NotNull
    private String zipCode;

    @Basic
    @Column(name = "CITY", nullable = false)
    @Size(min = 3, max = 100)
    @NotNull
    private String city;

    @Basic
    @Column(name = "COUNTRY_CODE", nullable = false)
    @Size(min = 2, max = 3)
    @NotNull
    private String countryCode;
}
