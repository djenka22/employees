package org.hyperoptic.model.entity;

import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "EMPLOYEE")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Employee {
    @Id
    @GeneratedValue(generator = "my_seq" )
    @SequenceGenerator(name = "my_seq", sequenceName = "my_seq", allocationSize = 1)
    private Long id;
    @Column(name = "name")
    @Size(max = 20)
    @NotNull
    private String name;

    @NotNull
    @NaturalId
    @Column(name = "personal_id")
    private BigDecimal personalId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    @NotNull
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_lead_id")
    @NotNull
    private Employee teamLead;

    public Employee() {
    }

    public Employee(Long id, String name, BigDecimal personalId, Team team, Employee teamLead) {
        this.id = id;
        this.name = name;
        this.personalId = personalId;
        this.team = team;
        this.teamLead = teamLead;
    }

    public BigDecimal getPersonalId() {
        return personalId;
    }

    public void setPersonalId(BigDecimal personalId) {
        this.personalId = personalId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Employee getTeamLead() {
        return teamLead;
    }

    public void setTeamLead(Employee teamLead) {
        this.teamLead = teamLead;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Employee employee = (Employee) o;

        if (!Objects.equals(id, employee.id)) return false;
        if (!name.equals(employee.name)) return false;
        if (!personalId.equals(employee.personalId)) return false;
        if (!team.getName().equals(employee.team.getName())) return false;
        return teamLead.equals(employee.teamLead);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (personalId != null ? personalId.hashCode() : 0);
        result = 31 * result + (team != null ? team.hashCode() : 0);
        result = 31 * result + (teamLead != null ? teamLead.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", personal id='" + personalId + '\'' +
                ", team=" + getTeam().getName() +
                ", teamLead=" + teamLead.getName() +
                '}';
    }

    public static class Builder {
        private Long id;
        private String name;
        private BigDecimal personalId;
        private Team team;
        private Employee teamLead;

        private Builder() {}

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withPersonalId(BigDecimal id) {
            this.personalId = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }

        public Builder withTeam(Team team) {
            this.team = team;
            return this;
        }

        public Builder withTeamLead(Employee teamLead) {
            this.teamLead = teamLead;
            return this;
        }

        public Employee build() {
            return new Employee(
                    id, name, personalId, team, teamLead
            );
        }
    }
}
