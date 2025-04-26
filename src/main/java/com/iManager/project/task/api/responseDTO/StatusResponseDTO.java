package com.iManager.project.task.api.responseDTO;

import java.util.UUID;

public class StatusResponseDTO {
    UUID id;
    String name;

    public StatusResponseDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public StatusResponseDTO() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
