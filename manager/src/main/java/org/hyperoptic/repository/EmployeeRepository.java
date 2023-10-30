package org.hyperoptic.repository;

import org.hyperoptic.model.entity.Employee;
import org.hyperoptic.repository.custom.CustomEmployeeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long>, JpaSpecificationExecutor<Employee>,
        CustomEmployeeRepository {
    @Modifying
    @Query("delete from Employee e where e.personalId =:naturalId")
    Integer deleteEmployeeByNaturalId(@Param("naturalId") BigDecimal naturalId);

    @Query("select count(*) from Employee e where e.personalId =:naturalId")
    Integer existsByNaturalId(@Param("naturalId") BigDecimal naturalId);
}
