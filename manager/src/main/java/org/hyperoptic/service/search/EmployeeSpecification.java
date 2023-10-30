package org.hyperoptic.service.search;

import org.hyperoptic.model.entity.Employee;
import org.hyperoptic.model.entity.Team;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;

public class EmployeeSpecification {

    public static Specification<Employee> and(Specification<Employee> current, Specification<Employee> toAdd) {
        if (current == null) {
            return toAdd;
        }
        return current.and(toAdd);
    }
    public static Specification<Employee> nameEqual(String name) {
        return (root, query, cb) ->
                cb.equal(root.get("name"), name);
    }

    public static Specification<Employee> personalIdEqual(String personalId) {
        return (root, query, cb) ->
                cb.equal(root.get("personalId"), personalId);
    }

    public static Specification<Employee> teamEqual(String team) {
        return (root, query, cb) -> {
            Join<Team, Employee> employeeTeam = root.join("team");
            return cb.equal(employeeTeam.get("name"), team);
        };
    }

    public static Specification<Employee> leadEqual(String lead) {
        return (root, query, cb) -> {
            Join<Employee, Employee> employeeLead = root.join("teamLead");
            return cb.equal(employeeLead.get("name"), lead);
        };
    }

}
