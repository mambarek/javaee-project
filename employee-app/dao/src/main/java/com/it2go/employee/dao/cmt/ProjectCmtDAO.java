package com.it2go.employee.dao.cmt;


import com.it2go.employee.dao.DomainEntity;
import com.it2go.employee.entities.Project;

@DomainEntity(clazz = Project.class)
public class ProjectCmtDAO extends EntityCmtDAO<Project> {
    @Override
    Class<Project> getEntityClass() {
        return Project.class;
    }
}
