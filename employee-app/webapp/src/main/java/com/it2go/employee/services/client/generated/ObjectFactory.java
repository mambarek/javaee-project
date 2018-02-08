
package com.it2go.employee.services.client.generated;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.it2go.employee.services.client.generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetEmployeeById_QNAME = new QName("http://services.employee.it2go.com/", "getEmployeeById");
    private final static QName _GetEmployeeByIdResponse_QNAME = new QName("http://services.employee.it2go.com/", "getEmployeeByIdResponse");
    private final static QName _Address_QNAME = new QName("http://services.employee.it2go.com/", "address");
    private final static QName _EntityRemovedException_QNAME = new QName("http://services.employee.it2go.com/", "EntityRemovedException");
    private final static QName _Person_QNAME = new QName("http://services.employee.it2go.com/", "person");
    private final static QName _Save_QNAME = new QName("http://services.employee.it2go.com/", "save");
    private final static QName _FindAllResponse_QNAME = new QName("http://services.employee.it2go.com/", "findAllResponse");
    private final static QName _EntityNotFoundException_QNAME = new QName("http://services.employee.it2go.com/", "EntityNotFoundException");
    private final static QName _EntityConcurrentModificationException_QNAME = new QName("http://services.employee.it2go.com/", "EntityConcurrentModificationException");
    private final static QName _FindAll_QNAME = new QName("http://services.employee.it2go.com/", "findAll");
    private final static QName _Employee_QNAME = new QName("http://services.employee.it2go.com/", "employee");
    private final static QName _SaveResponse_QNAME = new QName("http://services.employee.it2go.com/", "saveResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.it2go.employee.services.client.generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetEmployeeById }
     * 
     */
    public GetEmployeeById createGetEmployeeById() {
        return new GetEmployeeById();
    }

    /**
     * Create an instance of {@link GetEmployeeByIdResponse }
     * 
     */
    public GetEmployeeByIdResponse createGetEmployeeByIdResponse() {
        return new GetEmployeeByIdResponse();
    }

    /**
     * Create an instance of {@link Address }
     * 
     */
    public Address createAddress() {
        return new Address();
    }

    /**
     * Create an instance of {@link EntityRemovedException }
     * 
     */
    public EntityRemovedException createEntityRemovedException() {
        return new EntityRemovedException();
    }

    /**
     * Create an instance of {@link Person }
     * 
     */
    public Person createPerson() {
        return new Person();
    }

    /**
     * Create an instance of {@link Save }
     * 
     */
    public Save createSave() {
        return new Save();
    }

    /**
     * Create an instance of {@link FindAllResponse }
     * 
     */
    public FindAllResponse createFindAllResponse() {
        return new FindAllResponse();
    }

    /**
     * Create an instance of {@link EntityNotFoundException }
     * 
     */
    public EntityNotFoundException createEntityNotFoundException() {
        return new EntityNotFoundException();
    }

    /**
     * Create an instance of {@link EntityConcurrentModificationException }
     * 
     */
    public EntityConcurrentModificationException createEntityConcurrentModificationException() {
        return new EntityConcurrentModificationException();
    }

    /**
     * Create an instance of {@link Employee }
     * 
     */
    public Employee createEmployee() {
        return new Employee();
    }

    /**
     * Create an instance of {@link FindAll }
     * 
     */
    public FindAll createFindAll() {
        return new FindAll();
    }

    /**
     * Create an instance of {@link SaveResponse }
     * 
     */
    public SaveResponse createSaveResponse() {
        return new SaveResponse();
    }

    /**
     * Create an instance of {@link Project }
     * 
     */
    public Project createProject() {
        return new Project();
    }

    /**
     * Create an instance of {@link EmailAddress }
     * 
     */
    public EmailAddress createEmailAddress() {
        return new EmailAddress();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "getEmployeeById")
    public JAXBElement<GetEmployeeById> createGetEmployeeById(GetEmployeeById value) {
        return new JAXBElement<GetEmployeeById>(_GetEmployeeById_QNAME, GetEmployeeById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetEmployeeByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "getEmployeeByIdResponse")
    public JAXBElement<GetEmployeeByIdResponse> createGetEmployeeByIdResponse(GetEmployeeByIdResponse value) {
        return new JAXBElement<GetEmployeeByIdResponse>(_GetEmployeeByIdResponse_QNAME, GetEmployeeByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Address }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "address")
    public JAXBElement<Address> createAddress(Address value) {
        return new JAXBElement<Address>(_Address_QNAME, Address.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EntityRemovedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "EntityRemovedException")
    public JAXBElement<EntityRemovedException> createEntityRemovedException(EntityRemovedException value) {
        return new JAXBElement<EntityRemovedException>(_EntityRemovedException_QNAME, EntityRemovedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Person }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "person")
    public JAXBElement<Person> createPerson(Person value) {
        return new JAXBElement<Person>(_Person_QNAME, Person.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Save }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "save")
    public JAXBElement<Save> createSave(Save value) {
        return new JAXBElement<Save>(_Save_QNAME, Save.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "findAllResponse")
    public JAXBElement<FindAllResponse> createFindAllResponse(FindAllResponse value) {
        return new JAXBElement<FindAllResponse>(_FindAllResponse_QNAME, FindAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EntityNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "EntityNotFoundException")
    public JAXBElement<EntityNotFoundException> createEntityNotFoundException(EntityNotFoundException value) {
        return new JAXBElement<EntityNotFoundException>(_EntityNotFoundException_QNAME, EntityNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link EntityConcurrentModificationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "EntityConcurrentModificationException")
    public JAXBElement<EntityConcurrentModificationException> createEntityConcurrentModificationException(EntityConcurrentModificationException value) {
        return new JAXBElement<EntityConcurrentModificationException>(_EntityConcurrentModificationException_QNAME, EntityConcurrentModificationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "findAll")
    public JAXBElement<FindAll> createFindAll(FindAll value) {
        return new JAXBElement<FindAll>(_FindAll_QNAME, FindAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Employee }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "employee")
    public JAXBElement<Employee> createEmployee(Employee value) {
        return new JAXBElement<Employee>(_Employee_QNAME, Employee.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SaveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.employee.it2go.com/", name = "saveResponse")
    public JAXBElement<SaveResponse> createSaveResponse(SaveResponse value) {
        return new JAXBElement<SaveResponse>(_SaveResponse_QNAME, SaveResponse.class, null, value);
    }

}
