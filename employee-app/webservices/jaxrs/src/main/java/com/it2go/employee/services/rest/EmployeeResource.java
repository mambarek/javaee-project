package com.it2go.employee.services.rest;


import com.it2go.employee.dto.EmployeeTableItem;
import com.it2go.employee.dto.EmployeesSearchTemplate;
import com.it2go.employee.dto.search.SearchResult;
import com.it2go.employee.entities.Employee;
import com.it2go.framework.dao.EntityNotFoundException;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

/*
    @Path annotation must be on the implementation
    If the annotation is on the Interface Weld can't instantiate
    the injected beans. Path on the interface confuse the rst-Container
    witch implementation would be used for a given interface
 */
public interface EmployeeResource {

    @GET
    @Path("/employees/ping")
    @Produces({MediaType.TEXT_PLAIN})
    String ping();

    @POST
    @Path("/employees")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
    Employee save(final Employee employee);

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.TEXT_PLAIN})
    @Path("/employees/{id:[1-9][0-9]*}")
    Employee findById(@PathParam("id") final Long id) throws EntityNotFoundException;

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/employees")
    List<Employee> findAll();

    @POST
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
    @Path("/employee/search")
    SearchResult findAllEmployeeItems(final EmployeesSearchTemplate searchTemplate);
}
