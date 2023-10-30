package org.hyperoptic.repository.custom;

import org.hibernate.Session;
import org.hyperoptic.infrastructure.exceptions.custom.DataNotFoundException;
import org.hyperoptic.model.entity.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryImpl implements CustomEmployeeRepository {

    public final Logger LOGGER = LoggerFactory.getLogger(EmployeeRepositoryImpl.class);

    private final EntityManager entityManager;

    public EmployeeRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long findIdByName(String name) {
        Query query = entityManager.createQuery("SELECT e.id from Employee e where name=:name");
        query.setParameter("name", name);

        List<Long> resultList;
        try {
            resultList = (List<Long>) query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error while executing query: {}. Error message: {}",
                    query.toString(), e.getMessage());
            throw e;
        }

        if(resultList.isEmpty()) {
            throw new DataNotFoundException("No employee with name " + name);
        }
        return resultList.get(0);
    }

    @Override
    public String findNameById(Long id) {
        Query query = entityManager.createQuery("SELECT e.name from Employee e where id=:id");
        query.setParameter("id", id);

        List<String> resultList;
        try {
            resultList = (List<String>) query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error while executing query: {}. Error message: {}",
                    query.toString(), e.getMessage());
            throw e;
        }

        if(resultList.isEmpty()) {
            throw new DataNotFoundException("No employee with id " + id);
        }
        return resultList.get(0);
    }

    @Override
    public Optional<Employee> findByNaturalId(BigDecimal naturalId) {
        Session session = entityManager.unwrap(Session.class);
        return session.bySimpleNaturalId(Employee.class).loadOptional(naturalId);
    }

    @Override
    public String findTeamNameByNaturalId(BigDecimal naturalId) {
        Query query = entityManager.createQuery("SELECT t.name from Employee e join Team t on e.team.id = t.id " +
                "where e.personalId=:naturalId");
        query.setParameter("naturalId", naturalId);

        List<String> resultList;
        try {
            resultList = (List<String>) query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error while executing query: {}. Error message: {}",
                    query.toString(), e.getMessage());
            throw e;
        }

        if(resultList.isEmpty()) {
            throw new DataNotFoundException("No team for Employe with personal id " + naturalId);
        }
        return resultList.get(0);
    }

    @Override
    public String findTeamLeadNameByNaturalId(BigDecimal naturalId) {
        Query query = entityManager.createQuery("SELECT e.teamLead.id from Employee e " +
                "where e.personalId=:naturalId");
        Query queryLeadEmployee = entityManager.createQuery("SELECT e.name from Employee e where e.id=:id ");
        query.setParameter("naturalId", naturalId);

        List<Long> resultList;
        try {
            resultList = (List<Long>) query.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error while executing query: {}. Error message: {}",
                    query.toString(), e.getMessage());
            throw e;
        }
        if(resultList.isEmpty()) {
            throw new DataNotFoundException("No Employee with personal id " + naturalId);
        }
        Long teadLeadId = resultList.get(0);

        queryLeadEmployee.setParameter("id", teadLeadId);

        List<String> resultListLeadName;
        try {
            resultListLeadName = (List<String>) queryLeadEmployee.getResultList();
        } catch (Exception e) {
            LOGGER.error("Error while executing query: {}. Error message: {}",
                    query.toString(), e.getMessage());
            throw e;
        }
        if(resultList.isEmpty()) {
            throw new DataNotFoundException("No Employee with personal id " + naturalId);
        }
        return resultListLeadName.get(0);

    }
}
