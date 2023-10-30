package org.hyperoptic.repository.custom;


public interface CustomTeamRepository {
    Long findIdByName(String name);
    String findNameById(Long id);


}
