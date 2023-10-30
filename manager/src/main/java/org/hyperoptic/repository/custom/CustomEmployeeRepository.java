package org.hyperoptic.repository.custom;

import org.hyperoptic.model.entity.Employee;

import java.math.BigDecimal;
import java.util.Optional;

public interface CustomEmployeeRepository {
    Long findIdByName(String name);

    String findNameById(Long id);
    Optional<Employee> findByNaturalId(BigDecimal naturalId);
    String findTeamNameByNaturalId(BigDecimal naturalId);
    String findTeamLeadNameByNaturalId(BigDecimal naturalId);


}
