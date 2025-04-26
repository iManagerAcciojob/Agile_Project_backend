package com.iManager.project.task.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.project.task.api.responseDTO.ProjectResponseDTO;
import com.iManager.project.task.api.responseDTO.UserResponseDTO;
import com.iManager.project.task.api.util.DbApi;
import com.iManager.project.task.api.util.Mapper;
import com.iManager.project.task.api.util.TokenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/member")
public class MemberController {

    TokenApi tokenApi;
    DbApi dbApi;
    Mapper mapper;
    ObjectMapper objectMapper;

    public MemberController(TokenApi tokenApi, DbApi dbApi,
                             Mapper mapper, ObjectMapper objectMapper) {
        this.tokenApi = tokenApi;
        this.dbApi = dbApi;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @GetMapping("/get/{orgId}")
    public ResponseEntity getMembers(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable UUID orgId) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.getMembers(orgId);
                    List<UserResponseDTO> responseDTOList = objectMapper.convertValue(object, new TypeReference<List<UserResponseDTO>>() {});
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

    @GetMapping("/subProject/{subProjectId}")
    public ResponseEntity getSubProjectMembers(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable UUID subProjectId) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.getSubProjectMembers(subProjectId);
                    List<UserResponseDTO> responseDTOList = objectMapper.convertValue(object, new TypeReference<List<UserResponseDTO>>() {});
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

    @PostMapping("/add/role/{subProjectId}/{userId}/{roleId}")
    public ResponseEntity addMembers(@RequestHeader("Authorization") String authHeader,
                                     @PathVariable UUID subProjectId,
                                     @PathVariable UUID userId,
                                     @PathVariable UUID roleId) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    Object object = dbApi.addMemberRole(subProjectId,userId,roleId);
                    UserResponseDTO responseDTO = objectMapper.convertValue(object, new TypeReference<UserResponseDTO>() {});
                    return new ResponseEntity(responseDTO, HttpStatus.OK);
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
