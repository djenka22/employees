package org.hyperoptic.service.employee;

import org.hyperoptic.infrastructure.exceptions.custom.BadRequestException;
import org.hyperoptic.infrastructure.exceptions.custom.DataNotFoundException;
import org.hyperoptic.model.employee.EmployeeResponseCreate;
import org.hyperoptic.model.employee.EmployeeDtoBasic;
import org.hyperoptic.model.entity.Employee;
import org.hyperoptic.service.client.EmployeeClient;
import org.hyperoptic.service.mapper.UtilMapper;
import org.hyperoptic.service.search.EmployeeFilters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.hyperoptic.service.search.EmployeeSpecification.*;

public abstract class EmployeeServiceAbstract implements EmployeeService {

    public final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceAbstract.class);
    protected final EmployeeClient employeeClient;

    protected final UtilMapper utilMapper;

    public EmployeeServiceAbstract(EmployeeClient employeeClient, UtilMapper utilMapper) {
        this.employeeClient = employeeClient;
        this.utilMapper = utilMapper;
    }

    @Override
    public <D> EmployeeResponseCreate create(D dto) {
        return createEmployee(dto);
    }


    @Override
    public List<EmployeeDtoBasic> findAll(Pageable pageable) {
        Page<Employee> employees = employeeClient.findAll(pageable);
        List<EmployeeDtoBasic> employeeDtoBasicList = new ArrayList<>();
        for(Employee employee : employees) {
            EmployeeDtoBasic employeeDtoBasic = utilMapper.createEmployeeDtoBasic(employee);
            employeeDtoBasicList.add(employeeDtoBasic);
        }
        return employeeDtoBasicList;
    }

    @Override
    public <D,B> EmployeeDtoBasic update(D dto, B identifier) {
        return updateEmployee(dto, identifier);
    }

    @Override
    public <B> void delete(B identifier) {
        String naturalId = (String) identifier;
        if(!employeeClient.existByNaturalId(new BigDecimal(naturalId))) {
            throw new DataNotFoundException("No employee with personal id " + naturalId);
        }
        employeeClient.delete(new BigDecimal(naturalId));
    }

    @Override
    public List<EmployeeDtoBasic> search(EmployeeFilters employeeFilters, Pageable pageable) {

        Specification<Employee> specification = createSpecification(employeeFilters);

        Page<Employee> employees = employeeClient.search(specification, pageable);
        if (employees.isEmpty()) {
            throw new DataNotFoundException("No such employee");
        }
        List<EmployeeDtoBasic> employeeDtoBasicList = new ArrayList<>();
        for(Employee employee : employees) {
            EmployeeDtoBasic employeeDtoBasic = utilMapper.createEmployeeDtoBasic(employee);
            employeeDtoBasicList.add(employeeDtoBasic);
        }
        return employeeDtoBasicList;
    }

    private Specification<Employee> createSpecification(EmployeeFilters employeeFilters) {
        Specification<Employee> specification = null;
        if (employeeFilters.getName() != null) {
            specification = nameEqual(employeeFilters.getName().trim());
        }
        if (employeeFilters.getPersonalId() != null) {
            specification = and(specification, personalIdEqual(employeeFilters.getPersonalId().trim()));
        }
        if (employeeFilters.getTeam() != null) {
            specification = and(specification, teamEqual(employeeFilters.getTeam()));
        }
        if (employeeFilters.getLead() != null) {
            specification = and(specification, leadEqual(employeeFilters.getLead()));
            LOGGER.info((specification != null) + "");
        }
        if (specification == null) {
            throw new BadRequestException("All filters are empty");
        }
        return specification;
    }

    protected EmployeeDtoBasic fillMissingFields(Employee employee, EmployeeDtoBasic employeeDtoBasic) {
        String teamName = employeeClient.findTeamNameNaturalId(employee.getPersonalId());
        String teamLeadName = employeeClient.findTeamLeadNameNaturalId(employee.getPersonalId());
        return new EmployeeDtoBasic(employeeDtoBasic, teamName, teamLeadName);

    }

    public abstract <D> EmployeeResponseCreate createEmployee(D dto);
    public abstract <D,B> EmployeeDtoBasic updateEmployee(D dto, B identifier);
}
