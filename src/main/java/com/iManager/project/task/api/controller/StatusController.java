package com.iManager.project.task.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.project.task.api.requestDTO.RoleRequestDTO;
import com.iManager.project.task.api.responseDTO.RoleResponseDTO;
import com.iManager.project.task.api.responseDTO.StatusResponseDTO;
import com.iManager.project.task.api.util.DbApi;
import com.iManager.project.task.api.util.Mapper;
import com.iManager.project.task.api.util.TokenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/status")
public class StatusController {

    TokenApi tokenApi;
    DbApi dbApi;
    Mapper mapper;
    ObjectMapper objectMapper;

    public StatusController(TokenApi tokenApi, DbApi dbApi,
                          Mapper mapper, ObjectMapper objectMapper) {
        this.tokenApi = tokenApi;
        this.dbApi = dbApi;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create/{subProjectId}/{statusName}")
    public ResponseEntity createStatus(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable UUID subProjectId,
                                     @PathVariable String statusName) {
        try {
            ResponseEntity response = tokenApi.tokenVerify(authHeader);
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.createStatus(subProjectId,statusName);
                    StatusResponseDTO responseDTO = objectMapper.convertValue(object, new TypeReference<StatusResponseDTO>() {});
                    return new ResponseEntity(responseDTO, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity("Failed creating status", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{subProjectId}")
    public ResponseEntity getStatus(@RequestHeader("Authorization") String authHeader,
                                   @PathVariable UUID subProjectId) {
        try {
            ResponseEntity response = tokenApi.tokenVerify(authHeader);
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.getStatus(subProjectId);
                    List<StatusResponseDTO> responseDTO = objectMapper.convertValue(object, new TypeReference<List<StatusResponseDTO>>() {});
                    return new ResponseEntity(responseDTO, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity("Failed fetching status", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
