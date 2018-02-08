
package com.it2go.employee.services.client.generated;

import javax.xml.ws.WebFault;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b14002
 * Generated source version: 2.2
 * 
 */
@WebFault(name = "EntityRemovedException", targetNamespace = "http://services.employee.it2go.com/")
public class EntityRemovedException_Exception
    extends Exception
{

    /**
     * Java type that goes as soapenv:Fault detail element.
     * 
     */
    private EntityRemovedException faultInfo;

    /**
     * 
     * @param faultInfo
     * @param message
     */
    public EntityRemovedException_Exception(String message, EntityRemovedException faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @param faultInfo
     * @param cause
     * @param message
     */
    public EntityRemovedException_Exception(String message, EntityRemovedException faultInfo, Throwable cause) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }

    /**
     * 
     * @return
     *     returns fault bean: com.it2go.employee.services.client.generated.EntityRemovedException
     */
    public EntityRemovedException getFaultInfo() {
        return faultInfo;
    }

}