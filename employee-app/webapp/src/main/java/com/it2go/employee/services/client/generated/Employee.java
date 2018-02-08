
package com.it2go.employee.services.client.generated;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java-Klasse für employee complex type.
 * 
 * <p>Das folgende Schemafragment gibt den erwarteten Content an, der in dieser Klasse enthalten ist.
 * 
 * <pre>
 * &lt;complexType name="employee">
 *   &lt;complexContent>
 *     &lt;extension base="{http://services.employee.it2go.com/}person">
 *       &lt;sequence>
 *         &lt;element name="projects" type="{http://services.employee.it2go.com/}project" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="salary" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/>
 *         &lt;element name="traveling" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="weekendWork" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "employee", propOrder = {
    "projects",
    "salary",
    "traveling",
    "weekendWork"
})
public class Employee
    extends Person
{

    @XmlElement(nillable = true)
    protected List<Project> projects;
    protected Double salary;
    protected boolean traveling;
    protected boolean weekendWork;

    /**
     * Gets the value of the projects property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the projects property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getProjects().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Project }
     * 
     * 
     */
    public List<Project> getProjects() {
        if (projects == null) {
            projects = new ArrayList<Project>();
        }
        return this.projects;
    }

    /**
     * Ruft den Wert der salary-Eigenschaft ab.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getSalary() {
        return salary;
    }

    /**
     * Legt den Wert der salary-Eigenschaft fest.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setSalary(Double value) {
        this.salary = value;
    }

    /**
     * Ruft den Wert der traveling-Eigenschaft ab.
     * 
     */
    public boolean isTraveling() {
        return traveling;
    }

    /**
     * Legt den Wert der traveling-Eigenschaft fest.
     * 
     */
    public void setTraveling(boolean value) {
        this.traveling = value;
    }

    /**
     * Ruft den Wert der weekendWork-Eigenschaft ab.
     * 
     */
    public boolean isWeekendWork() {
        return weekendWork;
    }

    /**
     * Legt den Wert der weekendWork-Eigenschaft fest.
     * 
     */
    public void setWeekendWork(boolean value) {
        this.weekendWork = value;
    }

}
