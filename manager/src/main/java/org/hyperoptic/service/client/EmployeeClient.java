package org.hyperoptic.service.client;

import org.hyperoptic.model.entity.Employee;
import org.hyperoptic.repository.EmployeeRepository;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

public class EmployeeClient implements Client<Employee> {
    private final EmployeeRepository employeeRepository;
    private final TeamClient teamClient;

    public EmployeeClient(EmployeeRepository employeeRepository, TeamClient teamClient) {
        this.employeeRepository = employeeRepository;
        this.teamClient = teamClient;
    }

    public Long findTeamIdByName(String name) {
        return teamClient.findIdByName(name);
    }

    @Override
    public Long findIdByName(String name) {
        return employeeRepository.findIdByName(name);
    }

    @Override
    public Employee create(Employee entity) {
        return employeeRepository.save(entity);
    }

    public Page<Employee> findAll(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Optional<Employee> findByNaturalId(BigDecimal naturalId) {
        return employeeRepository.findByNaturalId(naturalId);
    }

    @Override
    public Employee update(Employee entity) {
        return employeeRepository.save(entity);
    }

    @Override
    @Transactional
    public <B> void delete(B identifier) {
        BigDecimal naturalId = (BigDecimal) identifier;
        employeeRepository.deleteEmployeeByNaturalId(naturalId);
    }

    public String findTeamNameNaturalId(BigDecimal naturalId) {
        return employeeRepository.findTeamNameByNaturalId(naturalId);
    }
    public String findTeamLeadNameNaturalId(BigDecimal naturalId) {
        return employeeRepository.findTeamLeadNameByNaturalId(naturalId);
    }

    public boolean existByNaturalId(BigDecimal naturalId) {
        Integer count = employeeRepository.existsByNaturalId(naturalId);
        return !count.equals(0);
    }

    public Page<Employee> search(Specification<Employee> specification, Pageable pageable) {
        return employeeRepository.findAll(specification, pageable);
    }
}
