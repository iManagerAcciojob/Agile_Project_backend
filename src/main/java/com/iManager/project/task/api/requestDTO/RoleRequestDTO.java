package com.iManager.project.task.api.requestDTO;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleRequestDTO {
    UUID id;
    String name;
    String description;
    UUID orgId;

    public RoleRequestDTO() {
    }

    public RoleRequestDTO(UUID id, String name, String description, UUID orgId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.orgId = orgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getOrgId() {
        return orgId;
    }

    public void setOrgId(UUID orgId) {
        this.orgId = orgId;
    }
}
