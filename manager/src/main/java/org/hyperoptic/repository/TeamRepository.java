package org.hyperoptic.repository;

import org.hyperoptic.model.entity.Team;
import org.hyperoptic.repository.custom.CustomTeamRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long>, JpaSpecificationExecutor<Team>, CustomTeamRepository {

    boolean existsByName(String name);
    Optional<Team> findByName(String team);

    @Modifying
    @Query("delete from Team t where t.name =:name")
    Integer deleteByName(@Param("name") String name);
}
