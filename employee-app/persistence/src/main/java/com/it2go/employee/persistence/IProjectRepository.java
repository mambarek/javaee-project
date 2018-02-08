package com.it2go.employee.persistence;


import com.it2go.employee.entities.Project;

import java.time.LocalDate;
import java.util.List;

public interface IProjectRepository extends IDomainEntityRepository<Project> {
    public List<Project> findByName(String name);
    public List<Project> findByBeginDate(LocalDate beginDate);
}
