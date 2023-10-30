package org.hyperoptic.model.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmployeeDtoBasic {
    @Size(max = 20)
    @NotNull
    @JsonProperty("name")
    private String name;

    @Pattern(regexp="^\\d+$", message = "personal_id must have only digits")
    @NotNull
    @JsonProperty("personal_id")
    @Length(min = 6, max = 6, message = "personal_id must have exactly 6 digits")
    private String personalId;
    @Size(max = 50)
    @NotNull
    @JsonProperty("team")
    private String team;
    @NotNull
    @JsonProperty("team_lead")
    private String teamLead;

    public EmployeeDtoBasic() {
    }

    public EmployeeDtoBasic(EmployeeDtoBasic employeeDtoBasic, String team, String lead) {
        this.name = employeeDtoBasic.getName();
        this.personalId = employeeDtoBasic.getPersonalId();
        this.team = team;
        this.teamLead = lead;
    }
    @JsonCreator
    public EmployeeDtoBasic(@JsonProperty("name") String name,
                            @JsonProperty("personal_id") String personalId,
                            @JsonProperty("team") String team,
                            @JsonProperty("team_lead") String teamLead) {
        this.name = name;
        this.team = team;
        this.teamLead = teamLead;
        this.personalId = personalId;
    }

    public String getPersonalId() {
        return personalId;
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

    public void setName(String name) {
        this.name = name;
    }

    public void setPersonalId(String personalId) {
        this.personalId = personalId;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public void setTeamLead(String teamLead) {
        this.teamLead = teamLead;
    }

    @Override
    public String toString() {
        return "EmployeeDtoBasic{" +
                "name='" + name + '\'' +
                ", personalId='" + personalId + '\'' +
                ", team='" + team + '\'' +
                ", teamLead='" + teamLead + '\'' +
                '}';
    }
}
