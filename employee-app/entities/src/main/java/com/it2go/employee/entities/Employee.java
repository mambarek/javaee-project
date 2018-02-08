package com.it2go.employee.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.ws.rs.Produces;
import javax.xml.bind.annotation.XmlRootElement;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@Entity
@XmlRootElement
public class Employee extends Person {

    @Basic
    @Column(name = "SALARY")
    private Double salary;

    @Column(name = "WEEKEND_WORK")
    private boolean weekendWork;

    @Column(name = "TRAVELING")
    private boolean traveling;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "employee", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Project> projects = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", orphanRemoval = true, fetch = FetchType.LAZY)
    private List<File> documents = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("Employee[(%s)%s %s (%s EUR)]",this.getId(), this.getFirstName(), this.getLastName(), this.getSalary());
    }

    public void addProject(Project project){
        Objects.requireNonNull(project);
        project.setEmployee(this);

        projects.add(project);
    }

    public void removeProject(Project project){
        Objects.requireNonNull(project);
        project.setEmployee(null);
        this.projects.remove(project);
    }

    public void addDocument(File document){
        Objects.requireNonNull(document);
        document.setOwner(this);
        documents.add(document);
    }

    public void remoDocument(File document){
        Objects.requireNonNull(document);
        document.setOwner(null);
        documents.remove(document);
    }
}