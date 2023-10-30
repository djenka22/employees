package org.hyperoptic.repository.custom;

import org.hyperoptic.infrastructure.exceptions.custom.DataNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class TeamRepositoryImpl implements CustomTeamRepository {
    public final Logger LOGGER = LoggerFactory.getLogger(TeamRepositoryImpl.class);

    private final EntityManager entityManager;

    public TeamRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Long findIdByName(String name) {
        Query query = entityManager.createQuery("SELECT t.id from Team t where name=:name");
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
            throw new DataNotFoundException("No team with name " + name);
        }
        return resultList.get(0);
    }

    @Override
    public String findNameById(Long id) {
        Query query = entityManager.createQuery("SELECT t.name from Team t where id=:id");
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
            throw new DataNotFoundException("No team with id " + id);
        }
        return resultList.get(0);
    }
}
