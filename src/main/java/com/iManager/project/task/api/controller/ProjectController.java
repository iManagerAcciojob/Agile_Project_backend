package com.iManager.project.task.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.project.task.api.requestDTO.ProjectRequestDTO;
import com.iManager.project.task.api.responseDTO.ProjectResponseDTO;
import com.iManager.project.task.api.util.DbApi;
import com.iManager.project.task.api.util.Mapper;
import com.iManager.project.task.api.util.TokenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/project")
public class ProjectController {
    
    TokenApi tokenApi;
    DbApi dbApi;
    Mapper mapper;
    ObjectMapper objectMapper;

    public ProjectController(TokenApi tokenApi, DbApi dbApi,
                             Mapper mapper, ObjectMapper objectMapper) {
        this.tokenApi = tokenApi;
        this.dbApi = dbApi;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity createProject(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody ProjectRequestDTO requestDTO) {
        try {
            ResponseEntity response = tokenApi.tokenVerify(authHeader);
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.createProject(requestDTO);
                    ProjectResponseDTO responseDTO = objectMapper.convertValue(object, new TypeReference<ProjectResponseDTO>() {});
                    return new ResponseEntity(responseDTO, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity("Failed creating Project", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{projectId}")
    public ResponseEntity deleteProject(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable UUID projectId) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    dbApi.deleteProject(projectId);
                    return new ResponseEntity("Project Deleted Successfully", HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity("Failed deleting Project", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateProject(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody ProjectRequestDTO requestDTO) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    dbApi.updateProject(requestDTO);
                    return new ResponseEntity("Project Updated Successfully", HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity("Failed Updating Project", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity getProject(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable UUID orgId) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.getProject(orgId);
                    List<ProjectResponseDTO> responseDTOList = objectMapper.convertValue(object, new TypeReference<List<ProjectResponseDTO>>() {});
                    return new ResponseEntity(responseDTOList, HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity("Failed getting Project", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
