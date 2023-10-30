package org.hyperoptic.service.employee;

import org.hyperoptic.infrastructure.exceptions.custom.BadRequestException;
import org.hyperoptic.infrastructure.exceptions.custom.DataNotFoundException;
import org.hyperoptic.model.employee.EmployeeDtoUpdate;
import org.hyperoptic.model.employee.EmployeeResponseCreate;
import org.hyperoptic.model.employee.EmployeeDtoBasic;
import org.hyperoptic.model.entity.Employee;
import org.hyperoptic.model.entity.Team;
import org.hyperoptic.service.client.EmployeeClient;
import org.hyperoptic.service.mapper.UtilMapper;
import org.hyperoptic.service.mapper.employee.EmployeeMapperImpl;

import java.math.BigDecimal;

public class EmployeeServiceImpl extends EmployeeServiceAbstract {
    public static String SUCCESSFUL_RESPONSE = "created successfully";
    public EmployeeServiceImpl(EmployeeClient employeeClient, UtilMapper utilMapper) {
        super(employeeClient, utilMapper);
    }

    @Override
    public <D> EmployeeResponseCreate createEmployee(D dto) {
        EmployeeDtoBasic employeeDtoBasic = (EmployeeDtoBasic) dto;
        Long teamId = employeeClient.findTeamIdByName(employeeDtoBasic.getTeam().trim());
        Long leadId = employeeClient.findIdByName(employeeDtoBasic.getTeamLead().trim());

        EmployeeMapperImpl employeeMapperImpl = utilMapper.createEmployeeMapperDto(teamId, leadId);
        Employee employeeToSave = employeeMapperImpl.map(employeeDtoBasic);

        Employee savedEmployee = employeeClient.create(employeeToSave);
        return utilMapper.createEmployeeResponse(savedEmployee);
    }

    @Override
    public <D, B> EmployeeDtoBasic updateEmployee(D dto, B identifier) {
        EmployeeDtoUpdate employeeDtoUpdate = (EmployeeDtoUpdate) dto;
        String naturalId = (String) identifier;

        Employee employee = employeeClient.findByNaturalId(new BigDecimal(naturalId.trim()))
                .orElseThrow(() -> new DataNotFoundException("No employee with personal id " + naturalId));
        updateEmployeeFields(employee, employeeDtoUpdate);
        Employee updatedEmployee = employeeClient.update(employee);
        EmployeeDtoBasic employeeDtoBasic = utilMapper.createEmployeeDtoBasic(updatedEmployee);
        LOGGER.info(employeeDtoBasic.toString());
        EmployeeDtoBasic employeeDtoBasicFull = fillMissingFields(updatedEmployee, employeeDtoBasic);
        LOGGER.info(employeeDtoBasicFull.toString());
        return employeeDtoBasicFull;
    }




    private void updateEmployeeFields(Employee employee, EmployeeDtoUpdate employeeDtoUpdate) {
        boolean allFieldsEmpty = true;

        if (employeeDtoUpdate.getName() != null && !employeeDtoUpdate.getName().isEmpty()) {
            employee.setName(employeeDtoUpdate.getName());
            allFieldsEmpty = false;
        }
        if (employeeDtoUpdate.getTeam() != null && !employeeDtoUpdate.getTeam().isEmpty()) {
            Long teamId = employeeClient.findTeamIdByName(employeeDtoUpdate.getTeam());
            Team team = Team.builder().withId(teamId).build();
            employee.setTeam(team);
            allFieldsEmpty = false;
        }
        if (employeeDtoUpdate.getTeamLead() != null && !employeeDtoUpdate.getTeamLead().isEmpty()) {
            Long leadId = employeeClient.findIdByName(employeeDtoUpdate.getTeamLead());
            Employee leadEmployee = Employee.builder().withId(leadId).build();
            employee.setTeamLead(leadEmployee);
            allFieldsEmpty = false;
        }

        if (allFieldsEmpty) {
            throw new BadRequestException("All fields are empty, nothing to update");
        }

    }


}
