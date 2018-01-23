package com.it2go.employee.services.rest.test;

import com.it2go.employee.entities.Employee;
import com.it2go.employee.services.rest.EmployeeResource;
import com.it2go.framework.dao.EntityNotFoundException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

@RunWith(Arquillian.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class EmployeeResourceTest {

    private static WebTarget target;

    /**
     * Arquillian specific method for creating a file which can be deployed
     * while executing the test.
     *
     * @return a war file
     */
    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        // You can use war packaging...
        WebArchive war = ShrinkWrap.create(WebArchive.class, "test.war")
                //.addPackage(Employee.class.getPackage())
                .addPackages(true, "com.it2go")
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource("wildfly-ds.xml")
                //.addAsLibraries(files)
                //.addAsLibraries(resolver.artifact("com.it2go:jpa-model:1.0-SNAPSHOT").resolveAsFiles())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

        return war;
    }

    @ArquillianResource
    private URL base;

    @Before
    public void setupClass() throws MalformedURLException {
        Client client = ClientBuilder.newClient();
        target = client.target(URI.create(new URL(base, "rest/EmployeeService/employees/").toExternalForm()));
    }
/*    @BeforeClass
    public static void setUpClass() {

        // initializes the rest easy client framework
        RegisterBuiltin.register(ResteasyProviderFactory.getInstance());
    }*/

    @Test
    public void test00(){
        //String r = target.path("ping").request().get(String.class);
        //System.out.println(">>>>>>>>>>>>>>>>>>> r = " + r);
        System.out.println("Hello from EmployeeRepositoryServiceTest!!!!!!!!");
        System.out.println("++++ deploymentURL = " + base);

        try {
            String uri = base.toString() +"index.html";//+ "EmployeeService/employees/ping";
            URL url = new URL(uri);
            final URLConnection connection = url.openConnection();
            connection.getInputStream();

            InputStream iStream =  connection.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(iStream, "utf8"));
            StringBuffer sb = new StringBuffer();
            String line = "";

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            System.out.println(sb.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void test01(@ArquillianResteasyResource("rest/EmployeeService/employees") WebTarget webTarget){
        System.out.println(">>> test01");
        Employee employee = new Employee();
        employee.setFirstName("X-Rest");
        employee.setLastName("Y-Rest");
        employee.setSalary(4000d);

/*        String ping = webTarget.path("/ping").request().get(String.class);
        System.out.println("ping = " + ping);*/

/*        webTarget.request(MediaType.APPLICATION_JSON)
                .post(Entity.json(employee));*/
        target.request(MediaType.APPLICATION_JSON)
                .post(Entity.json("{\"firstName\": \"Ali\", \"lastName\": \"Mbarek\"}"));
/*         webTarget
                .request()
                .post(Entity.entity(employee, MediaType.APPLICATION_JSON_TYPE));*/


    }

    @Test
    public void test02(@ArquillianResteasyResource("rest/EmployeeService/employees") WebTarget webTarget){
        System.out.println(">>> test02");

/*        final Employee result = target.path("id/1L")
                .request(MediaType.APPLICATION_XML)
                .get(Employee.class);*/

        Employee result = target
                .path("{id}")
                .resolveTemplate("id", "1")
                .request(MediaType.APPLICATION_XML)
                .get(Employee.class);

        System.out.println("result = " + result);
    }

    @Test
    public void test03(@ArquillianResteasyResource("rest/EmployeeService/employees") WebTarget webTarget){
        System.out.println(">>> test03");

/*        final Employee result = target.path("id/1L")
                .request(MediaType.APPLICATION_XML)
                .get(Employee.class);*/

/*        Employee result = target
                //.path("{id}")
                //.resolveTemplate("id", "1")
                .request(MediaType.APPLICATION_XML)
                .get(Employee.class);*/

        final Employee[] employees = target.request(MediaType.APPLICATION_XML).get(Employee[].class);
        System.out.println("result employees = " + employees.length);
        System.out.println("result first employees = " + employees[0]);
    }

    @Test
    public void test04(@ArquillianResteasyResource("rest/EmployeeService") EmployeeResource employeeResource) throws EntityNotFoundException {
        System.out.println(">>> test04");
/*        Employee employee = new Employee();
        employee.setFirstName("X-Rest");
        employee.setLastName("Y-Rest");
        employee.setSalary(4000);*/

        //Employee employee1 = employeeResource.put(employee);
        final Employee employee1 = employeeResource.findById(1L);
        System.out.println("employee1 = " + employee1);
    }
}
