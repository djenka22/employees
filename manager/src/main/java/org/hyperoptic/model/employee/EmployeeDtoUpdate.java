package org.hyperoptic.model.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeDtoUpdate {

    @Size(max = 20)
    private String name;
    @Size(max = 50)
    private String team;
    private String teamLead;

    public EmployeeDtoUpdate() {
    }

    @JsonCreator
    public EmployeeDtoUpdate(@JsonProperty("name") String name,
                             @JsonProperty("team") String team,
                             @JsonProperty("team_lead") String teamLead) {
        this.name = name;
        this.team = team;
        this.teamLead = teamLead;
    }

    public String getName() {
        return name;
    }

    public String getTeam() {
        return team;
    }

    public String getTeamLead() {
        return teamLead;
    }

}
