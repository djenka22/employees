package org.hyperoptic.service.search;

import org.hyperoptic.model.entity.Employee;
import org.springframework.data.jpa.domain.Specification;

import javax.validation.constraints.Pattern;

public class EmployeeFilters {
    private String name;
    private String team;
    private String personalId;
    private String lead;

    public EmployeeFilters(String name, String team, String personalId, String lead) {
        this.name = name;
        this.team = team;
        this.personalId = personalId;
        this.lead = lead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPersonalId() {
        return personalId;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public String getLead() {
        return lead;
    }

    public void setLead(String lead) {
        this.lead = lead;
    }
}
