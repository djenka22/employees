package org.hyperoptic.model.employee;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EmployeeResponseCreate {
    private String name;
    private String personalId;
    private String message;

    public EmployeeResponseCreate() {
    }

    @JsonCreator
    public EmployeeResponseCreate(@JsonProperty("name") String name,
                                  @JsonProperty("personal_id") String personalId,
                                  @JsonProperty("message") String message) {
        this.name = name;
        this.personalId = personalId;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public String getPersonalId() {
        return personalId;
    }

    public String getMessage() {
        return message;
    }
}
