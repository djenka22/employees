package org.hyperoptic.model.team;

public class TeamDtoResponseCreate {
    private String name;
    private String message;

    public TeamDtoResponseCreate() {
    }

    public TeamDtoResponseCreate(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
