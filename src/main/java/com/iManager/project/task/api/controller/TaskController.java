package com.iManager.project.task.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.project.task.api.requestDTO.SubProjectReqDTO;
import com.iManager.project.task.api.requestDTO.TaskRequestDTO;
import com.iManager.project.task.api.responseDTO.SubProjectResDTO;
import com.iManager.project.task.api.responseDTO.TaskResponseDTO;
import com.iManager.project.task.api.util.DbApi;
import com.iManager.project.task.api.util.Mapper;
import com.iManager.project.task.api.util.TokenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    TokenApi tokenApi;
    DbApi dbApi;
    Mapper mapper;
    ObjectMapper objectMapper;

    public TaskController(TokenApi tokenApi, DbApi dbApi,
                          Mapper mapper, ObjectMapper objectMapper) {
        this.tokenApi = tokenApi;
        this.dbApi = dbApi;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity createTask(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody TaskRequestDTO requestDTO) {
        try {
            ResponseEntity response = tokenApi.tokenVerify(authHeader);
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.createTask(requestDTO);
                    TaskResponseDTO responseDTO = objectMapper.convertValue(object, new TypeReference<TaskResponseDTO>() {});
                    return new ResponseEntity(responseDTO, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity("Failed creating Task", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{taskId}")
    public ResponseEntity deleteTask(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable UUID taskId) {
        try {
            ResponseEntity response = tokenApi.tokenVerify(authHeader);
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    dbApi.deleteTask(taskId);
                    return new ResponseEntity("Task deleted Successfully", HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity("Failed deleting Task", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateTask(@RequestHeader("Authorization") String authHeader,
                                           @RequestBody TaskRequestDTO requestDTO) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    dbApi.updateTask(requestDTO);
                    return new ResponseEntity("task updated successfully", HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity("Failed Updating subProject", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{subProjectId}")
    public ResponseEntity getTask(@RequestHeader("Authorization") String authHeader,
                                  @PathVariable UUID subProjectId) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.getTask(subProjectId);
                    List<TaskResponseDTO> responseDTOList = objectMapper.convertValue(object, new TypeReference<List<TaskResponseDTO>>() {});
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
