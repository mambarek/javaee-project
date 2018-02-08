package com.it2go.employee.scheduler;

import com.it2go.employee.entities.Employee;
import com.it2go.employee.persistence.EmployeeRepository;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.employee.services.EmployeeRepositoryService;
import com.it2go.employee.ui.controller.TestController;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityRemovedException;
import com.it2go.masterdata.Gender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
public class CreateEmployeesJob implements Job {

    @Inject
    private EmployeeRepository employeeRepository;

    @Inject
    private UserSession userSession;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("-- CreateEmployeesJob::execute");

        final List<Employee> all = employeeRepository.findAll();
        Employee employee = new Employee();
        employee.setFirstName("Name_"+all.size());
        employee.setLastName("Lastname_"+all.size());
        if(all.size() % 2 == 0)
            employee.setGender(Gender.FEMALE);
        else
            employee.setGender(Gender.MALE);

        try {
            final Employee employee1 = employeeRepository.persist(employee, userSession.getTestCreationUser());
            System.out.println("-- CreateEmployeesJob::execute employee persisted " + employee1);
        } catch (EntityConcurrentModificationException e) {
            e.printStackTrace();
        } catch (EntityRemovedException e) {
            e.printStackTrace();
        }
    }
}
