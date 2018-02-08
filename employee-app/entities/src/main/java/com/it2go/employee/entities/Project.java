package com.it2go.employee.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@SuppressWarnings("JpaDataSourceORMInspection")
@Data
@Entity
public class Project extends DomainEntity {

    @Basic
    @Column(name = "NAME", nullable = false)
    @Size(min = 3, max = 100)
    @NotNull
    private String name;

    @Basic
    @Column(name = "BUDGET")
    private double budget;

    @Basic
    @Column(name = "BEGIN")
    @Temporal(TemporalType.DATE)
    private Date begin;

    @Basic
    @Column(name = "END")
    @Temporal(TemporalType.DATE)
    private Date end;

    @Column(name = "TIL_TODAY")
    private boolean tilToday;

    @Basic
    @Column(name = "DESCRIPTION")
    @Size(min = 50, max = 500)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "EMPL_ID")
    private Employee employee;

}
