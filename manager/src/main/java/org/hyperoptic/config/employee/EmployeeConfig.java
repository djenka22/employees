package org.hyperoptic.config.employee;

import org.hyperoptic.repository.EmployeeRepository;
import org.hyperoptic.repository.TeamRepository;
import org.hyperoptic.repository.custom.CustomEmployeeRepository;
import org.hyperoptic.repository.custom.EmployeeRepositoryImpl;
import org.hyperoptic.service.client.Client;
import org.hyperoptic.service.client.EmployeeClient;
import org.hyperoptic.service.client.TeamClient;
import org.hyperoptic.service.employee.EmployeeService;
import org.hyperoptic.service.employee.EmployeeServiceImpl;
import org.hyperoptic.service.mapper.UtilMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

@Configuration
public class EmployeeConfig {

    @Bean
    public UtilMapper utilMapper() {
        return new UtilMapper();
    }

    @Bean
    public EmployeeService employeeService(EmployeeClient employeeClient, UtilMapper utilMapper) {
        return new EmployeeServiceImpl(employeeClient, utilMapper);
    }

    @Bean
    public EmployeeClient employeeClient(EmployeeRepository employeeRepository, TeamClient teamRepository) {
        return new EmployeeClient(employeeRepository, teamRepository);
    }

    @Bean
    public CustomEmployeeRepository employeeRepositoryImpl(EntityManager entityManager) {
        return new EmployeeRepositoryImpl(entityManager);
    }
}
