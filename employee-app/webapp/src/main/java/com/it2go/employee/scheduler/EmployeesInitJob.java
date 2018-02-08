package com.it2go.employee.scheduler;

import com.it2go.employee.entities.Employee;
import com.it2go.employee.persistence.EmployeeRepository;
import com.it2go.employee.persistence.IEmployeeRepository;
import com.it2go.employee.persistence.UserSession;
import com.it2go.employee.services.EmployeeRepositoryServiceImpl;
import com.it2go.employee.services.client.generated.EmployeeRepositoryService;
import com.it2go.employee.services.client.generated.EmployeeRepositoryService_Service;
import com.it2go.employee.services.client.generated.EntityConcurrentModificationException_Exception;
import com.it2go.employee.services.client.generated.EntityRemovedException_Exception;
import com.it2go.employee.ui.controller.TestController;
import com.it2go.framework.dao.EntityConcurrentModificationException;
import com.it2go.framework.dao.EntityRemovedException;
import com.it2go.masterdata.Gender;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import javax.ejb.EJB;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@ApplicationScoped
public class EmployeesInitJob implements Job {

    @Inject
    private IEmployeeRepository employeeRepository;

    @Inject
    private UserSession userSession;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //System.out.println("-- EmployeesInitJob::execute begin");
        cdiJob();
        //System.out.println("-- EmployeesInitJob::execute finish");
        /*try {
            webserviceJob();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }*/

/*        final List<Employee> all = employeeRepository.findAll();
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
        }*/
    }

    private void cdiJob() {

        final List<Employee> all = employeeRepository.findAll();

        Employee employee = new Employee();

        employee.setFirstName("Name_"+all.size());
        employee.setLastName("Lastname_"+all.size());

        if(all.size() % 2 == 0)
            employee.setGender(Gender.FEMALE);
        else
            employee.setGender(Gender.MALE);

        try {
            final Employee employee1 = employeeRepository.persist(employee,null);
            //System.out.println("-- EmployeesInitJob::execute employee persisted " + employee1);
        } catch (EntityConcurrentModificationException | EntityRemovedException e) {
            e.printStackTrace();
        }
    }

    private void webserviceJob() throws MalformedURLException {
        String riUrl = "http://localhost:8080/webapp/EmployeeRepositoryService?wsdl";

        /*long delay = 5000L;

        try {
            Thread.sleep(delay);
        } catch (Exception ignore) {
        }*/

        URL wsdlLocation = new URL(riUrl);
        EmployeeRepositoryService_Service service = null;

        while (service == null) {
            try {
                service = new EmployeeRepositoryService_Service(wsdlLocation);
            }catch(Exception e){
                // do nothing
                // wait until the webservice is done
            }

        }

        final EmployeeRepositoryService employeeRepositoryService = service.getEmployeeRepositoryServiceImplPort();

        final List<com.it2go.employee.services.client.generated.Employee> all = employeeRepositoryService.findAll();

        com.it2go.employee.services.client.generated.Employee employee =
                new com.it2go.employee.services.client.generated.Employee();

        employee.setFirstName("Name_"+all.size());
        employee.setLastName("Lastname_"+all.size());

        if(all.size() % 2 == 0)
            employee.setGender(com.it2go.employee.services.client.generated.Gender.FEMALE);
        else
            employee.setGender(com.it2go.employee.services.client.generated.Gender.MALE);

        try {
            final com.it2go.employee.services.client.generated.Employee employee1 = employeeRepositoryService.save(employee);
            //System.out.println("-- EmployeesInitJob::execute employee persisted " + employee1);
        } catch (EntityRemovedException_Exception | EntityConcurrentModificationException_Exception e) {
            e.printStackTrace();
        }
    }
}
