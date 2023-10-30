package org.hyperoptic.service.employee;

import org.hyperoptic.model.employee.EmployeeDtoBasic;
import org.hyperoptic.model.employee.EmployeeResponseCreate;
import org.hyperoptic.service.CrudService;
import org.hyperoptic.service.search.EmployeeFilters;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService extends CrudService<EmployeeDtoBasic, EmployeeResponseCreate> {
    List<EmployeeDtoBasic> search(EmployeeFilters employeeFilters, Pageable pageable);
}
