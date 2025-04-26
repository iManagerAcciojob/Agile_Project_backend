package com.iManager.project.task.api.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.project.task.api.requestDTO.SubProjectReqDTO;
import com.iManager.project.task.api.responseDTO.SubProjectResDTO;
import com.iManager.project.task.api.util.DbApi;
import com.iManager.project.task.api.util.Mapper;
import com.iManager.project.task.api.util.TokenApi;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/subProject")
public class SubProjectController {

    TokenApi tokenApi;
    DbApi dbApi;
    Mapper mapper;
    ObjectMapper objectMapper;

    public SubProjectController(TokenApi tokenApi, DbApi dbApi,
                             Mapper mapper, ObjectMapper objectMapper) {
        this.tokenApi = tokenApi;
        this.dbApi = dbApi;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/create")
    public ResponseEntity createSubProject(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody SubProjectReqDTO requestDTO) {
        try {
            ResponseEntity response = tokenApi.tokenVerify(authHeader);
            if (response.getStatusCode() == HttpStatus.OK) {
                try {
                    System.out.println("subproject create");
                    Object object = dbApi.createSubProject(requestDTO);
                    SubProjectResDTO responseDTO = objectMapper.convertValue(object,new TypeReference<SubProjectResDTO>() {});
                    return new ResponseEntity(responseDTO, HttpStatus.CREATED);
                } catch (Exception e) {
                    return new ResponseEntity("Failed creating subProject", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/delete/{subProjectId}")
    public ResponseEntity deleteSubProject(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable UUID subProjectId) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    System.out.println("subproject delete");
                    dbApi.deleteSubProject(subProjectId);
                    return new ResponseEntity("subProject Deleted Successfully", HttpStatus.OK);
                } catch (Exception e) {
                    return new ResponseEntity("Failed deleting subProject", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity("Unauthorized access", HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to Verify Token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity updateSubProject(@RequestHeader("Authorization") String authHeader,
                                        @RequestBody SubProjectReqDTO requestDTO) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    System.out.println("subproject update");
                    dbApi.updateSubProject(requestDTO);
                    return new ResponseEntity("subProject updated successfully", HttpStatus.OK);
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

    @GetMapping("/get/{projectId}")
    public ResponseEntity getSubProject(@RequestHeader("Authorization") String authHeader,
                                        @PathVariable UUID projectId) {
        try {
            if (tokenApi.tokenVerify(authHeader).getStatusCode() == HttpStatus.OK) {
                try {
                    System.out.println("subproject list");
                    Object object = dbApi.getSubProject(projectId);
                    List<SubProjectResDTO> responseDTOList = objectMapper.convertValue(object, new TypeReference<List<SubProjectResDTO>>() {});
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
