package com.iManager.project.task.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.project.task.api.requestDTO.ProjectRequestDTO;
import com.iManager.project.task.api.requestDTO.RoleRequestDTO;
import com.iManager.project.task.api.responseDTO.ProjectResponseDTO;
import com.iManager.project.task.api.responseDTO.RoleResponseDTO;
import com.iManager.project.task.api.util.DbApi;
import com.iManager.project.task.api.util.Mapper;
import com.iManager.project.task.api.util.TokenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/role")
public class RoleController {

    TokenApi tokenApi;
    DbApi dbApi;
    Mapper mapper;
    ObjectMapper objectMapper;

    public RoleController(TokenApi tokenApi, DbApi dbApi,
                             Mapper mapper, ObjectMapper objectMapper) {
        this.tokenApi = tokenApi;
        this.dbApi = dbApi;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity createRole(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody RoleRequestDTO requestDTO) {
        try {
            ResponseEntity response = tokenApi.tokenVerify(authHeader);
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.createRole(requestDTO);
                    RoleResponseDTO responseDTO = objectMapper.convertValue(object, new TypeReference<RoleResponseDTO>() {});
                    return new ResponseEntity(responseDTO, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity("Failed creating Role", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity getRoles(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable UUID orgId) {
        try {
            ResponseEntity response = tokenApi.tokenVerify(authHeader);
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.getRoles(orgId);
                    List<RoleResponseDTO> responseDTO = objectMapper.convertValue(object, new TypeReference<List<RoleResponseDTO>>() {});
                    return new ResponseEntity(responseDTO, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity("Failed creating Role", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
