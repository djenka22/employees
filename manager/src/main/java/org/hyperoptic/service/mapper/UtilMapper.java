package org.hyperoptic.service.mapper;

import org.hyperoptic.infrastructure.exceptions.custom.LazyLoadException;
import org.hyperoptic.model.employee.EmployeeDtoBasic;
import org.hyperoptic.model.employee.EmployeeResponseCreate;
import org.hyperoptic.model.entity.Employee;
import org.hyperoptic.service.mapper.employee.EmployeeMapperImpl;

import java.util.HashMap;
import java.util.Map;

import static org.hyperoptic.service.employee.EmployeeServiceImpl.SUCCESSFUL_RESPONSE;

public class UtilMapper {
    public EmployeeMapperImpl createEmployeeMapperDto(Long teamId, Long leadId) {
        Map<String, Long> ids = new HashMap<>();
        ids.put(EmployeeMapperImpl.TEAM_ID, teamId);
        ids.put(EmployeeMapperImpl.LEAD_ID, leadId);
        return new EmployeeMapperImpl(ids);
    }

    public EmployeeResponseCreate createEmployeeResponse(Employee employee) {
        return new EmployeeResponseCreate(
                employee.getName(), String.valueOf(employee.getPersonalId()), SUCCESSFUL_RESPONSE
        );
    }

    public EmployeeDtoBasic createEmployeeDtoBasic(Employee employee) {
        EmployeeDtoBasic employeeDtoBasic = null;
        try {
            employeeDtoBasic = new EmployeeDtoBasic(
                    employee.getName(), String.valueOf(employee.getPersonalId()),
                    employee.getTeam().getName(), employee.getTeamLead().getName()
            );
        } catch (Exception e) {
            throw new LazyLoadException(e.getMessage());
        }
        return employeeDtoBasic;
    }
}
