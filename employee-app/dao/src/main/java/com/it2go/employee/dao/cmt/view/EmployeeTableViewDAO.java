package com.it2go.employee.dao.cmt.view;

import com.it2go.employee.dto.EmployeeTableItem;
import com.it2go.employee.dto.EmployeesSearchTemplate;
import com.it2go.employee.dto.search.Group;
import com.it2go.employee.dto.search.GroupOperation;
import com.it2go.employee.dto.search.Operation;
import com.it2go.employee.dto.search.Rule;
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
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<EmployeeTableItem> criteriaQuery = cb.createQuery(EmployeeTableItem.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        Join<Employee,Person> join = employeeRoot.join(Employee_.createdBy, JoinType.LEFT);

        final CompoundSelection<EmployeeTableItem> compoundSelection = cb.construct(EmployeeTableItem.class,
                employeeRoot.get(Employee_.id),
                employeeRoot.get(Employee_.firstName),
                employeeRoot.get(Employee_.lastName),
                employeeRoot.get(Employee_.birthDate),
                employeeRoot.get(Employee_.salary),
                employeeRoot.get(Employee_.createdBy).get(Person_.firstName),
                employeeRoot.get(Employee_.createdAt));

        final CriteriaQuery<EmployeeTableItem> select = criteriaQuery.select(compoundSelection);

        Map<String,Object> paramMap = new HashMap<>();
        if(employeesSearchTemplate != null){
            List<Predicate> predicates = new ArrayList<>();
            // firstName
            final String firstName = employeesSearchTemplate.getEmployeeTableItem().getFirstName();
            if(StringUtils.exists(firstName)){
                Expression<String> namePath = employeeRoot.get(Employee_.firstName);
                final Predicate predicate = cb.like(employeeRoot.get(Employee_.firstName), "%" + firstName + "%");
                //select.where(predicate);
                //predicates.add(predicate);
            }

            // lastName
            final String lastName = employeesSearchTemplate.getEmployeeTableItem().getLastName();
            if(StringUtils.exists(lastName)){
                Expression<String> namePath = employeeRoot.get(Employee_.lastName);
                final Predicate predicate = cb.like(employeeRoot.get(Employee_.lastName), "%" + lastName + "%");
                //select.where(predicate);
                //predicates.add(predicate);
            }


            if(employeesSearchTemplate.getFilters() != null){
                Group group = employeesSearchTemplate.getFilters();
                Predicate groupPredicat = null;
                List<Predicate> rulesPredicate = new ArrayList<>();

                for (Rule rule: group.getRules()) {
                    Predicate rulePredicate = null;
                    switch (rule.getOp()){
                        case CONTAINS:
                            rulePredicate = cb.like(employeeRoot.get(rule.getField()), "%" + rule.getData() + "%");
                            break;
                        case LESS:
                            rulePredicate = cb.lessThan(employeeRoot.get(rule.getField()), rule.getData());
                            break;
                        case LESS_OR_EQUAL:
                            rulePredicate = cb.lessThanOrEqualTo(employeeRoot.get(rule.getField()), rule.getData());
                            break;
                        case NULL:
                            rulePredicate = cb.isNull(employeeRoot.get(rule.getField()));
                            break;
                        case NOT_NULL:
                            rulePredicate = cb.isNotNull(employeeRoot.get(rule.getField()));
                            break;
                        case EQUAL:
                            rulePredicate = cb.equal(employeeRoot.get(rule.getField()),  rule.getData());
                            break;
                        case NOT_EQUAL:
                            rulePredicate = cb.notEqual(employeeRoot.get(rule.getField()),  rule.getData());
                            break;
                        case IN:{
                            ParameterExpression<String> nameParameter = cb.parameter(String.class, rule.getField());
                            paramMap.put(rule.getField(), rule.getData());
                            final Expression<Integer> locate = cb.locate(nameParameter, employeeRoot.get(rule.getField()));
                            rulePredicate = cb.gt(locate,0);
                            break;}
                        case NOT_IN:{
                            ParameterExpression<String> nameParameter = cb.parameter(String.class, rule.getField());
                            paramMap.put(rule.getField(), rule.getData());
                            final Expression<Integer> locate = cb.locate(nameParameter, employeeRoot.get(rule.getField()));
                            rulePredicate = cb.equal(locate,0);
                            break;}
                        case BETWEEN:
                            break;
                        case GREATHER:
                            rulePredicate = cb.greaterThan(employeeRoot.get(rule.getField()), rule.getData());
                            break;
                        case ENDS_WITH:
                            rulePredicate = cb.like(employeeRoot.get(rule.getField()), "%" + rule.getData());
                            break;
                        case BEGINS_WITH:
                            rulePredicate = cb.like(employeeRoot.get(rule.getField()), rule.getData() + "%");
                            break;
                        case NOT_CONTAINS:
                            rulePredicate = cb.notLike(employeeRoot.get(rule.getField()), "%" + rule.getData() + "%");
                            break;
                        case NOT_ENDS_WITH:
                            rulePredicate = cb.notLike(employeeRoot.get(rule.getField()), "%" + rule.getData());
                            break;
                        case NOT_BEGIN_WITH:
                            rulePredicate = cb.notLike(employeeRoot.get(rule.getField()), rule.getData() + "%");
                            break;
                        case GREATHER_OR_EQUAL:
                            rulePredicate = cb.greaterThanOrEqualTo(employeeRoot.get(rule.getField()), rule.getData());
                            break;

                    }


                    if(rulePredicate != null){
                        rulesPredicate.add(rulePredicate);
                    }
                }

                switch (group.getGroupOp()){
                    case AND:
                        groupPredicat = cb.and(rulesPredicate.toArray(new Predicate[0]));
                        break;
                    case OR:
                        groupPredicat = cb.or(rulesPredicate.toArray(new Predicate[0]));
                        break;
                }

                predicates.add(groupPredicat);
            }


            select.where(predicates.toArray(new Predicate[0]));


            // Order by
            Order orderBy = null;

            if(StringUtils.exists(employeesSearchTemplate.getOrderBy())) {
                switch (employeesSearchTemplate.getOrderDirection()) {
                    case "asc":
                        orderBy = cb.asc(employeeRoot.get(employeesSearchTemplate.getOrderBy()));
                        break;
                    case "desc":
                        orderBy = cb.desc(employeeRoot.get(employeesSearchTemplate.getOrderBy()));
                        break;
                }
            }

            if(orderBy != null)
                select.orderBy(orderBy);

        }


        final TypedQuery<EmployeeTableItem> query = entityManager.createQuery(select);

        // set query parameter if exists
        paramMap.forEach(query::setParameter);

        if(employeesSearchTemplate != null) {
            if(employeesSearchTemplate.getMaxResult() > 0)
                query.setMaxResults(employeesSearchTemplate.getMaxResult());

            query.setFirstResult(employeesSearchTemplate.getOffset());
        }

        final List<EmployeeTableItem> resultList = query.getResultList();
        System.out.println("resultList = " + resultList.size());

        return resultList;
    }

    public Long countEmployees(EmployeesSearchTemplate employeesSearchTemplate){

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);


        final CriteriaQuery<Long> count = criteriaQuery.select(criteriaBuilder.count(employeeRoot));


        if(employeesSearchTemplate != null){
            List<Predicate> predicates = new ArrayList<>();
            // firstName
            final String firstName = employeesSearchTemplate.getEmployeeTableItem().getFirstName();
            if(StringUtils.exists(firstName)){
                Expression<String> namePath = employeeRoot.get(Employee_.firstName);
                final Predicate predicate = criteriaBuilder.like(employeeRoot.get(Employee_.firstName), "%" + firstName + "%");
                //select.where(predicate);
                //predicates.add(predicate);
            }

            // lastName
            final String lastName = employeesSearchTemplate.getEmployeeTableItem().getLastName();
            if(StringUtils.exists(lastName)){
                Expression<String> namePath = employeeRoot.get(Employee_.lastName);
                final Predicate predicate = criteriaBuilder.like(employeeRoot.get(Employee_.lastName), "%" + lastName + "%");
                //select.where(predicate);
                //predicates.add(predicate);
            }

            if(employeesSearchTemplate.getFilters() != null){
                Group group = employeesSearchTemplate.getFilters();
                Predicate groupPredicat = null;
                List<Predicate> rulesPredicate = new ArrayList<>();

                for (Rule rule: group.getRules()) {
                    Predicate rulePredicate = null;
                    if(rule.getOp() == Operation.CONTAINS){
                        rulePredicate = criteriaBuilder.like(employeeRoot.get(rule.getField()), "%" + rule.getData() + "%");
                    }

                    if(rulePredicate != null){
                        rulesPredicate.add(rulePredicate);
                    }
                }

                switch (group.getGroupOp()){
                    case AND:
                        groupPredicat = criteriaBuilder.and(rulesPredicate.toArray(new Predicate[0]));
                        break;
                    case OR:
                        groupPredicat = criteriaBuilder.or(rulesPredicate.toArray(new Predicate[0]));
                        break;
                }

                predicates.add(groupPredicat);
            }


            count.where(predicates.toArray(new Predicate[0]));


        }


        final TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);


        final Long countResult = query.getSingleResult();

        return countResult;
    }
}
