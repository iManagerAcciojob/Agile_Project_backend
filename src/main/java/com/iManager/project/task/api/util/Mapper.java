package com.iManager.project.task.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iManager.project.task.api.requestDTO.ProjectRequestDTO;
import com.iManager.project.task.api.responseDTO.ProjectResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Mapper {
    @Autowired
    ObjectMapper objectMapper;

    public ProjectResponseDTO projectDtoMapper(Object object){
            Map<String,Object> map = objectMapper.convertValue(object,Map.class);
            Object ob = map.get("body");
        ProjectResponseDTO projectDTO = objectMapper.convertValue(ob, ProjectResponseDTO.class);
            return projectDTO;
    }
}
