package org.hyperoptic.service.mapper.employee;

import org.hyperoptic.infrastructure.exceptions.custom.MappingNotAvailableException;
import org.hyperoptic.model.employee.EmployeeDtoBasic;
import org.hyperoptic.model.entity.Employee;
import org.hyperoptic.model.entity.Team;

import java.math.BigDecimal;
import java.util.Map;

public class EmployeeMapperImpl implements EmployeeMapper {

    public static String TEAM_ID = "teamId";
    public static String LEAD_ID = "leadId";
    private Map<String, Long> ids;

    public EmployeeMapperImpl(Map<String, Long> ids) {
        this.ids = ids;
    }

    @Override
    public <D> Employee map(D dto) {
        if (ids != null && ids.size() != 2) {
            throw new MappingNotAvailableException("There should be two ids for mapping employee. Instead there are: " + ids.size());
        }
        EmployeeDtoBasic employeeDtoBasic = (EmployeeDtoBasic) dto;
        Team team = Team.builder()
                .withId(ids.get(TEAM_ID))
                .build();

        Employee teamLead = Employee.builder()
                .withId(ids.get(LEAD_ID))
                .build();

        return Employee.builder()
                .withName(employeeDtoBasic.getName())
                .withPersonalId(new BigDecimal(employeeDtoBasic.getPersonalId()))
                .withTeam(team)
                .withTeamLead(teamLead)
                .build();
    }
}
