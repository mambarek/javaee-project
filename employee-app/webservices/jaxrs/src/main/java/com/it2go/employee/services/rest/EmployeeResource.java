package com.it2go.employee.services.rest;


import com.it2go.employee.dto.EmployeeTableItem;
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

public interface EmployeeResource {

    @GET
    @Path("/employees/ping")
    @Produces({MediaType.TEXT_PLAIN})
    String ping();

    @POST
    @Path("/employees")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    // http://localhost:8080/webapp/rest/EmployeeService/employee/filter?_search=false&nd=1528382176889&rows=8&page=0&sidx=&sord=asc
    @Path("/employee/filter")
    List<EmployeeTableItem> findAllEmployeeItems(

            @QueryParam("_search") final boolean _search,
            @QueryParam("nd") final Long nd,
            @QueryParam("rows") final int rows,
            @QueryParam("page") final int page,
            @QueryParam("sidx") final String sidx,
            @QueryParam("sord") final String sord
    );

    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/employee/search")
    List<EmployeeTableItem> findAllEmployeeItems(

            @QueryParam("_search") final boolean _search,
            @QueryParam("nd") final Long nd,
            @QueryParam("rows") final int rows,
            @QueryParam("page") final int page,
            @QueryParam("sidx") final String sidx,
            @QueryParam("sord") final String sord,
            @QueryParam("searchField") final String searchField,
            @QueryParam("searchString") final String searchString,
            @QueryParam("searchOper") final String searchOper
    );
}
