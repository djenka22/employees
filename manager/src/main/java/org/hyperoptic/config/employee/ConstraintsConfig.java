package org.hyperoptic.config.employee;

import org.hyperoptic.model.Constraints;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

@Configuration
public class ConstraintsConfig {
    @Bean
    public Constraints employeeConstraints() {
        Constraints constraints = new Constraints(new ArrayList<>());
        constraints.getConstraints().add("employee_personal_id");
        constraints.getConstraints().add("team_name");
        return constraints;
    }
}
