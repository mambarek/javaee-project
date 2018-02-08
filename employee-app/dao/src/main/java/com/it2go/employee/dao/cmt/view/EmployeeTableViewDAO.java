package com.it2go.employee.dao.cmt.view;

import com.it2go.employee.dto.EmployeeTableItem;
import com.it2go.employee.dto.EmployeesSearchTemplate;
import com.it2go.employee.entities.Employee;
import com.it2go.employee.entities.Employee_;
import com.it2go.employee.entities.Person;
import com.it2go.employee.entities.Person_;
import com.it2go.framework.util.StringUtils;
import org.hibernate.jpamodelgen.util.StringUtil;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CompoundSelection;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EmployeeTableViewDAO {

    @Inject
    private EntityManager entityManager;

    public List<EmployeeTableItem> getEmployeeItems() {
        String query = "select new com.it2go.employee.dto.EmployeeTableItem(empl.id, empl.firstName, empl.lastName, empl.birthDate, empl.salary, p.firstName, empl.createdAt)" +
                " from Employee as empl left outer join empl.createdBy as p";

        final List<EmployeeTableItem> result;

        TypedQuery<EmployeeTableItem> typedQuery = entityManager.createQuery(query, EmployeeTableItem.class);
        result = typedQuery.getResultList();

        return result;
    }

    public List<EmployeeTableItem> getEmployeeItems(Map<String, Object> params) {
        final StringBuilder query = new StringBuilder();

        query.append("select new com.it2go.employee.dto.EmployeeTableItem(empl.id, empl.firstName, empl.lastName, p.firstName, empl.createdAt)" +
                " from Employee as empl left outer join empl.createdBy as p ");

        String where = "";

        if(params != null){
            query.append(" where ");
            params.forEach((s, o) -> query.append("empl." + s + "=").append("'"+ o +"'"));
        }

        //System.out.println(">>> query = " + query);

        final List<EmployeeTableItem> result;

        TypedQuery<EmployeeTableItem> typedQuery = entityManager.createQuery(query.toString(), EmployeeTableItem.class);
        result = typedQuery.getResultList();

        return result;
    }

    public List<EmployeeTableItem> filterEmployees(EmployeesSearchTemplate employeesSearchTemplate){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<EmployeeTableItem> criteriaQuery = criteriaBuilder.createQuery(EmployeeTableItem.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        Join<Employee,Person> join = employeeRoot.join(Employee_.createdBy, JoinType.LEFT);

        final CompoundSelection<EmployeeTableItem> compoundSelection = criteriaBuilder.construct(EmployeeTableItem.class,
                employeeRoot.get(Employee_.id),
                employeeRoot.get(Employee_.firstName),
                employeeRoot.get(Employee_.lastName),
                employeeRoot.get(Employee_.birthDate),
                employeeRoot.get(Employee_.salary),
                employeeRoot.get(Employee_.createdBy).get(Person_.firstName),
                employeeRoot.get(Employee_.createdAt));

        final CriteriaQuery<EmployeeTableItem> select = criteriaQuery.select(compoundSelection);

        if(employeesSearchTemplate != null){
            List<Predicate> predicates = new ArrayList<>();
            // firstName
            final String firstName = employeesSearchTemplate.getEmployeeTableItem().getFirstName();
            if(StringUtils.exists(firstName)){
                Expression<String> namePath = employeeRoot.get(Employee_.firstName);
                final Predicate predicate = criteriaBuilder.like(employeeRoot.get(Employee_.firstName), "%" + firstName + "%");
                //select.where(predicate);
                predicates.add(predicate);
            }

            // lastName
            final String lastName = employeesSearchTemplate.getEmployeeTableItem().getLastName();
            if(StringUtils.exists(lastName)){
                Expression<String> namePath = employeeRoot.get(Employee_.lastName);
                final Predicate predicate = criteriaBuilder.like(employeeRoot.get(Employee_.lastName), "%" + lastName + "%");
                //select.where(predicate);
                predicates.add(predicate);
            }


            select.where(predicates.toArray(new Predicate[0]));


            // Order by
            Order orderBy = null;
            // ascending
            if("asc".equals(employeesSearchTemplate.getOrderDirection())){
                if(Employee_.firstName.getName().equals(employeesSearchTemplate.getOrderBy()))
                    orderBy = criteriaBuilder.asc(employeeRoot.get(Employee_.firstName));

                if(Employee_.lastName.getName().equals(employeesSearchTemplate.getOrderBy()))
                    orderBy = criteriaBuilder.asc(employeeRoot.get(Employee_.lastName));
            }

            // descending
            if("desc".equals(employeesSearchTemplate.getOrderDirection())){
                if(Employee_.firstName.getName().equals(employeesSearchTemplate.getOrderBy()))
                    orderBy = criteriaBuilder.desc(employeeRoot.get(Employee_.firstName));

                if(Employee_.lastName.getName().equals(employeesSearchTemplate.getOrderBy()))
                    orderBy = criteriaBuilder.desc(employeeRoot.get(Employee_.lastName));
            }

            if(orderBy != null)
                select.orderBy(orderBy);

        }


        final TypedQuery<EmployeeTableItem> query = entityManager.createQuery(select);
        if(employeesSearchTemplate != null) {
            if(employeesSearchTemplate.getMaxResult() > 0)
                query.setMaxResults(employeesSearchTemplate.getMaxResult());

            query.setFirstResult(employeesSearchTemplate.getOffset());
        }

        final List<EmployeeTableItem> resultList = query.getResultList();
        System.out.println("resultList = " + resultList.size());

        return resultList;
    }
}
