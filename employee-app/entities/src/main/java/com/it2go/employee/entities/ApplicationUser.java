package com.it2go.employee.entities;

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
@Table(name = "APP_USER")
@XmlRootElement
public class ApplicationUser extends Person {

    @Basic
    @Column(name = "USER_NAME", nullable = false)
    @Size(min = 5, max = 10)
    @NotNull
    private String userName;
}
