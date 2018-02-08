package com.it2go.employee.dao.cmt.view;

import com.it2go.employee.dto.EmployeeTableItem;
import com.it2go.employee.dto.EmployeesSearchTemplate;
import com.it2go.employee.dto.search.Group;
import com.it2go.employee.dto.search.Rule;
import com.it2go.employee.entities.Employee;
import com.it2go.employee.entities.Employee_;
import com.it2go.employee.entities.Person;
import com.it2go.employee.entities.Person_;
import com.it2go.framework.util.StringUtils;
import org.hibernate.criterion.MatchMode;

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
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeeTableViewDAO {

    @Inject
    private EntityManager entityManager;

    private static char ESCAPE_CHAR = '!';

    private static String escape(String value) {
        return value
                .replace("!", ESCAPE_CHAR + "!")
                .replace("%", ESCAPE_CHAR + "%")
                .replace("_", ESCAPE_CHAR + "_");
    }

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

        if (params != null) {
            query.append(" where ");
            params.forEach((s, o) -> query.append("empl." + s + "=").append("'" + o + "'"));
        }

        //System.out.println(">>> query = " + query);

        final List<EmployeeTableItem> result;

        TypedQuery<EmployeeTableItem> typedQuery = entityManager.createQuery(query.toString(), EmployeeTableItem.class);
        result = typedQuery.getResultList();

        return result;
    }

    private Map<String, Object> whereClauseComponents(EmployeesSearchTemplate searchTemplate, CriteriaBuilder cb, Root<Employee> employeeRoot) {
        Map<String, Object> paramMap = new HashMap<>();
        List<Predicate> predicates = new ArrayList<>();

        if (searchTemplate != null && searchTemplate.getFilters() != null) {

            Group group = searchTemplate.getFilters();
            Predicate groupPredicate = null;
            List<Predicate> rulesPredicate = new ArrayList<>();

            for (Rule rule : group.getRules()) {
                Predicate rulePredicate = null;

                // unique operator needs no value
                switch (rule.getOp()) {
                    case NULL: {
                        rulePredicate = cb.isNull(employeeRoot.get(rule.getField()));
                        break;
                    }
                    case NOT_NULL: {
                        rulePredicate = cb.isNotNull(employeeRoot.get(rule.getField()));
                        break;
                    }
                }

                if (StringUtils.exists(rule.getData())) {
                    final Object value;
                    boolean isDate = "date".equals(rule.getType().toLowerCase());
                    boolean isNumber = "number".equals(rule.getType().toLowerCase());

                    String escapedValue = escape(rule.getData());
                    final Expression path;
                    final ParameterExpression namedParameter;
                    if (isDate) {
                        namedParameter = cb.<Date>parameter(Date.class, rule.getField());
                        path = employeeRoot.<Date>get(rule.getField());
                        Date date = null;
                        try {
                            LocalDate localDate = LocalDate.parse(rule.getData());
                            date = Date.valueOf(localDate);
                        } catch (Exception e) {
                        }

                        value = date;
                    } else if (isNumber) {
                        namedParameter = cb.<Number>parameter(Number.class, rule.getField());
                        path = employeeRoot.<Number>get(rule.getField());
                        Number number = null;
                        try {
                            number = Double.parseDouble(rule.getData());
                        } catch (Exception e) {
                        }

                        value = number;
                    } else {
                        namedParameter = cb.<String>parameter(String.class, rule.getField());
                        path = employeeRoot.<String>get(rule.getField());
                        value = rule.getData();
                    }

                    paramMap.put(rule.getField(), value);

                    switch (rule.getOp()) {
                        case EQUAL: {
                            rulePredicate = cb.equal(path, namedParameter);
                            break;
                        }
                        case NOT_EQUAL: {
                                rulePredicate = cb.notEqual(path, namedParameter);
                            break;
                        }
                        case LESS: {
                                rulePredicate = cb.lessThan(path, namedParameter);
                            break;
                        }
                        case LESS_OR_EQUAL: {
                                rulePredicate = cb.lessThanOrEqualTo(path, namedParameter);
                            break;
                        }
                        case GREATHER: {
                                rulePredicate = cb.greaterThan(path, namedParameter);
                            break;
                        }
                        case GREATHER_OR_EQUAL: {
                                rulePredicate = cb.greaterThanOrEqualTo(path, namedParameter);
                            break;
                        }
                        case CONTAINS: {
                            paramMap.put(rule.getField(), MatchMode.ANYWHERE.toMatchString(escapedValue));
                            rulePredicate = cb.like(path, namedParameter, ESCAPE_CHAR);
                            break;
                        }
                        case NOT_CONTAINS: {
                            paramMap.put(rule.getField(), MatchMode.ANYWHERE.toMatchString(escapedValue));
                            rulePredicate = cb.notLike(path, namedParameter, ESCAPE_CHAR);
                            break;
                        }
                        case ENDS_WITH: {
                            paramMap.put(rule.getField(), MatchMode.END.toMatchString(escapedValue));
                            rulePredicate = cb.like(path, namedParameter, ESCAPE_CHAR);
                            break;
                        }
                        case NOT_ENDS_WITH: {
                            paramMap.put(rule.getField(), MatchMode.END.toMatchString(escapedValue));
                            rulePredicate = cb.notLike(path, namedParameter, ESCAPE_CHAR);
                            break;
                        }
                        case BEGINS_WITH: {
                            paramMap.put(rule.getField(), MatchMode.START.toMatchString(escapedValue));
                            rulePredicate = cb.like(path, namedParameter, ESCAPE_CHAR);
                            break;
                        }
                        case NOT_BEGIN_WITH: {
                            paramMap.put(rule.getField(), MatchMode.START.toMatchString(escapedValue));
                            rulePredicate = cb.notLike(path, namedParameter, ESCAPE_CHAR);
                            break;
                        }
                        case IN: {
                            final Expression<Integer> locate = cb.locate(namedParameter, path);
                            rulePredicate = cb.gt(locate, 0);
                            break;
                        }
                        case NOT_IN: {
                            final Expression<Integer> locate = cb.locate(namedParameter, path);
                            rulePredicate = cb.equal(locate, 0);
                            break;
                        }
                        case BETWEEN: {
                            break;
                        }
                    }
                }

                if (rulePredicate != null) {
                    rulesPredicate.add(rulePredicate);
                }
            }

            switch (group.getGroupOp()) {
                case AND:
                    groupPredicate = cb.and(rulesPredicate.toArray(new Predicate[0]));
                    break;
                case OR:
                    groupPredicate = cb.or(rulesPredicate.toArray(new Predicate[0]));
                    break;
            }

            predicates.add(groupPredicate);
        }

        Map<String, Object> resultMap = new HashMap<String, Object>();
        resultMap.put("paramMap", paramMap);
        resultMap.put("predicates", predicates);

        return resultMap;
    }

    public List<EmployeeTableItem> filterEmployees(EmployeesSearchTemplate employeesSearchTemplate) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<EmployeeTableItem> criteriaQuery = cb.createQuery(EmployeeTableItem.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);

        Join<Employee, Person> join = employeeRoot.join(Employee_.createdBy, JoinType.LEFT);

        final CompoundSelection<EmployeeTableItem> compoundSelection = cb.construct(EmployeeTableItem.class,
                employeeRoot.get(Employee_.id),
                employeeRoot.get(Employee_.firstName),
                employeeRoot.get(Employee_.lastName),
                employeeRoot.get(Employee_.birthDate),
                employeeRoot.get(Employee_.salary),
                employeeRoot.get(Employee_.traveling),
                employeeRoot.get(Employee_.weekendWork),
                employeeRoot.get(Employee_.createdBy).get(Person_.firstName),
                employeeRoot.get(Employee_.createdAt));

        final CriteriaQuery<EmployeeTableItem> select = criteriaQuery.select(compoundSelection);

        Map<String, Object> whereClauseComponentsMap = whereClauseComponents(employeesSearchTemplate, cb, employeeRoot);
        List<Predicate> predicates = (List<Predicate>) whereClauseComponentsMap.get("predicates");
        Map<String, Object> paramMap = (Map<String, Object>) whereClauseComponentsMap.get("paramMap");

        if (employeesSearchTemplate != null) {
            select.where(predicates.toArray(new Predicate[0]));

            // Order by
            Order orderBy = null;

            if (StringUtils.exists(employeesSearchTemplate.getOrderBy())) {
                switch (employeesSearchTemplate.getOrderDirection()) {
                    case "asc":
                        orderBy = cb.asc(employeeRoot.get(employeesSearchTemplate.getOrderBy()));
                        break;
                    case "desc":
                        orderBy = cb.desc(employeeRoot.get(employeesSearchTemplate.getOrderBy()));
                        break;
                }
            }

            if (orderBy != null)
                select.orderBy(orderBy);
        }

        final TypedQuery<EmployeeTableItem> query = entityManager.createQuery(select);

        // set query parameter if exists
        paramMap.forEach(query::setParameter);

        if (employeesSearchTemplate != null) {
            if (employeesSearchTemplate.getMaxResult() > 0)
                query.setMaxResults(employeesSearchTemplate.getMaxResult());

            query.setFirstResult(employeesSearchTemplate.getOffset());
        }

        final List<EmployeeTableItem> resultList = query.getResultList();
        System.out.println("resultList = " + resultList.size());

        return resultList;
    }

    public Long countEmployees(EmployeesSearchTemplate employeesSearchTemplate) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<Long> criteriaQuery = cb.createQuery(Long.class);
        Root<Employee> employeeRoot = criteriaQuery.from(Employee.class);


        final CriteriaQuery<Long> count = criteriaQuery.select(cb.count(employeeRoot));

        Map<String, Object> whereClauseComponentsMap = whereClauseComponents(employeesSearchTemplate, cb, employeeRoot);
        List<Predicate> predicates = (List<Predicate>) whereClauseComponentsMap.get("predicates");
        Map<String, Object> paramMap = (Map<String, Object>) whereClauseComponentsMap.get("paramMap");


        count.where(predicates.toArray(new Predicate[0]));
        final TypedQuery<Long> query = entityManager.createQuery(criteriaQuery);

        // set query parameter if exists
        paramMap.forEach(query::setParameter);

        final Long countResult = query.getSingleResult();

        return countResult;
    }
}
