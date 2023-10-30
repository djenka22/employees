package org.hyperoptic.model.team;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TeamDtoBasic {
    @JsonProperty("name")
    String name;

    public TeamDtoBasic() {
    }

    public TeamDtoBasic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
