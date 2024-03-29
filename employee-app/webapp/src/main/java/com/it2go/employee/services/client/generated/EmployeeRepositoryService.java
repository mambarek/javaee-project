
package com.it2go.employee.services.client.generated;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b14002
 * Generated source version: 2.2
 * 
 */
@WebService(name = "EmployeeRepositoryService", targetNamespace = "http://services.employee.it2go.com/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface EmployeeRepositoryService {


    /**
     * 
     * @param arg0
     * @return
     *     returns com.it2go.employee.services.client.generated.Employee
     * @throws EntityNotFoundException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "getEmployeeById", targetNamespace = "http://services.employee.it2go.com/", className = "com.it2go.employee.services.client.generated.GetEmployeeById")
    @ResponseWrapper(localName = "getEmployeeByIdResponse", targetNamespace = "http://services.employee.it2go.com/", className = "com.it2go.employee.services.client.generated.GetEmployeeByIdResponse")
    public Employee getEmployeeById(
        @WebParam(name = "arg0", targetNamespace = "")
        Long arg0)
        throws EntityNotFoundException_Exception
    ;

    /**
     * 
     * @param arg0
     * @return
     *     returns com.it2go.employee.services.client.generated.Employee
     * @throws EntityConcurrentModificationException_Exception
     * @throws EntityRemovedException_Exception
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "save", targetNamespace = "http://services.employee.it2go.com/", className = "com.it2go.employee.services.client.generated.Save")
    @ResponseWrapper(localName = "saveResponse", targetNamespace = "http://services.employee.it2go.com/", className = "com.it2go.employee.services.client.generated.SaveResponse")
    public Employee save(
        @WebParam(name = "arg0", targetNamespace = "")
        Employee arg0)
        throws EntityConcurrentModificationException_Exception, EntityRemovedException_Exception
    ;

    /**
     * 
     * @return
     *     returns java.util.List<com.it2go.employee.services.client.generated.Employee>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findAll", targetNamespace = "http://services.employee.it2go.com/", className = "com.it2go.employee.services.client.generated.FindAll")
    @ResponseWrapper(localName = "findAllResponse", targetNamespace = "http://services.employee.it2go.com/", className = "com.it2go.employee.services.client.generated.FindAllResponse")
    public List<Employee> findAll();

}
