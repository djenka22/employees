package org.hyperoptic.model.entity;


import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "team")
@Cacheable
public class Team {
    @Id
    @GeneratedValue(generator = "my_seq" )
    @SequenceGenerator(name = "my_seq", sequenceName = "my_seq", allocationSize = 1)
    private Long id;

    @Column(name = "name")
    @NotNull
    @Size(max = 50)
    @NaturalId(mutable = true)
    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<>();

    public Team() {
    }

    public Team(Long id, String name, Set<Employee> employees) {
        this.id = id;
        this.name = name;
        this.employees = employees;
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public static Builder builder() {
        return new Builder();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Team team = (Team) o;

        if (!Objects.equals(id, team.id)) return false;
        if (!name.equals(team.name)) return false;
        if (employees == team.employees)
            return true;
        if (employees.size() != team.employees.size())
            return false;
        return employees.containsAll(team.employees);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (employees != null ? employees.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static class Builder {
        private Long id;
        private String name;
        private Set<Employee> employees;

        private Builder() {}

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withName(String name) {
            this.name = name;
            return this;
        }
        public Builder withEmployees(Set<Employee> employees) {
            this.employees = employees;
            return this;
        }

        public Team build() {
            return new Team(id, name, employees);
        }
    }
}
