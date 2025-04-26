package com.iManager.project.task.api.util;

import com.iManager.project.task.api.requestDTO.*;
import com.iManager.project.task.api.responseDTO.StatusResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.UUID;

@Service
public class DbApi extends ApiUtilImpl{
    @Value("${service.db.url}")
    private String dbUrl;
    @Autowired
    RestTemplate restTemplate;


    public Object createProject(ProjectRequestDTO request){
        System.out.println("Inside project creating method");
        try {
            String endpoint = dbUrl+"/db/api/project/create";
            ResponseEntity response = makePostCall(request,endpoint,"",new HashMap<>());
            System.out.println("posted project successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed posting project");
            throw new RuntimeException("failed posting project");
        }
    }

    public void deleteProject(UUID projectId){
        System.out.println("Inside project creating method");
        try {
            String url = dbUrl+"/db/api/project/delete/"+projectId;
            RequestEntity request = RequestEntity.get(url).build();
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE,request, Object.class);
            System.out.println("posted Deleted successfully");
        }catch (Exception e){
            System.out.println("failed Deleting project");
            throw new RuntimeException("failed Deleting project");
        }
    }

    public ResponseEntity updateProject(ProjectRequestDTO requestDTO){
        System.out.println("Inside project creating method");
        try {
            String url = dbUrl+"/db/api/project/update";
            RequestEntity request = RequestEntity.post(url).body(requestDTO);
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.PUT,request, Object.class);
            System.out.println("posted Updated successfully");
            return response;
        }catch (Exception e){
            System.out.println("failed Deleting project");
            throw new RuntimeException("failed Deleting project");
        }
    }

    public Object getProject(UUID orgID){
        System.out.println("Inside project creating method");
        try {
            String url = dbUrl+"/db/api/project/get/"+orgID;
            ResponseEntity response = makeGetCall(url,"",new HashMap<>());
            System.out.println("posted Deleted successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed Deleting project");
            throw new RuntimeException("failed Deleting project");
        }
    }

    public Object createSubProject(SubProjectReqDTO request){
        System.out.println("Inside project creating method");
        try {
            String endpoint = dbUrl+"/db/api/subProject/create";
            ResponseEntity response = makePostCall(request,endpoint,"",new HashMap<>());
            System.out.println("posted project successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed posting project");
            throw new RuntimeException("failed posting project");
        }
    }

    public void deleteSubProject(UUID subProjectId){
        System.out.println("Inside subProject deleting method");
        try {
            String url = dbUrl+"/db/api/subProject/delete/"+subProjectId;
            RequestEntity request = RequestEntity.get(url).build();
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE,request, Object.class);
            System.out.println("Deleted successfully");
        }catch (Exception e){
            System.out.println("failed Deleting subProject");
            throw new RuntimeException("failed Deleting subProject");
        }
    }

    public ResponseEntity updateSubProject(SubProjectReqDTO requestDTO){
        System.out.println("Inside subProject updating method");
        try {
            String url = dbUrl+"/db/api/subProject/update";
            RequestEntity request = RequestEntity.post(url).body(requestDTO);
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.PUT,request, Object.class);
            System.out.println("Updated successfully");
            return response;
        }catch (Exception e){
            System.out.println("failed Updating subProject");
            throw new RuntimeException("failed Updating subProject");
        }
    }

    public Object getSubProject(UUID projectId){
        System.out.println("Inside subProject fetching method");
        try {
            String url = dbUrl+"/db/api/subProject/get/"+projectId;
            ResponseEntity response = makeGetCall(url,"",new HashMap<>());
            System.out.println("posted Deleted successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed Fetching subProject");
            throw new RuntimeException("failed Fetching subProject");
        }
    }

    public Object createTask(TaskRequestDTO request){
        System.out.println("Inside task creating method");
        try {
            String endpoint = dbUrl+"/db/api/task/create";
            ResponseEntity response = makePostCall(request,endpoint,"",new HashMap<>());
            System.out.println("created task successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed creating task");
            throw new RuntimeException("failed creating task");
        }
    }

    public void deleteTask(UUID taskId){
        System.out.println("Inside subProject deleting method");
        try {
            String url = dbUrl+"/db/api/task/delete/"+taskId;
            RequestEntity request = RequestEntity.get(url).build();
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.DELETE,request, Object.class);
            System.out.println("Deleted successfully");
        }catch (Exception e){
            System.out.println("failed Deleting subProject");
            throw new RuntimeException("failed Deleting subProject");
        }
    }

    public ResponseEntity updateTask(TaskRequestDTO requestDTO){
        System.out.println("Inside Task updating method");
        try {
            String url = dbUrl+"/db/api/task/update";
            RequestEntity request = RequestEntity.post(url).body(requestDTO);
            ResponseEntity response = restTemplate.exchange(url, HttpMethod.PUT,request, String.class);
            System.out.println("Updated successfully");
            return response;
        }catch (Exception e){
            System.out.println("failed Updating task");
            throw new RuntimeException("failed Updating task");
        }
    }

    public Object getTask(UUID subProjectId){
        System.out.println("Inside task fetching method");
        try {
            String url = dbUrl+"/db/api/task/get/"+subProjectId;
            ResponseEntity response = makeGetCall(url,"",new HashMap<>());
            System.out.println("task fetch successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed Fetching task");
            throw new RuntimeException("failed Fetching task");
        }
    }

    public Object createRole(RoleRequestDTO requestDTO) {
        System.out.println("Inside role creating method");
        try {
            String endpoint = dbUrl+"/db/api/role/create/"+requestDTO.getOrgId();
            ResponseEntity response = makePostCall(requestDTO,endpoint,"",new HashMap<>());
            System.out.println("created task successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed creating task");
            throw new RuntimeException("failed creating task");
        }
    }

    public Object getRoles(UUID orgId) {
        System.out.println("Inside role fetching method");
        try {
            String url = dbUrl+"/db/api/role/get/"+orgId;
            ResponseEntity response = makeGetCall(url,"",new HashMap<>());
            System.out.println("roles fetch successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed Fetching roles");
            throw new RuntimeException("failed Fetching roles");
        }
    }

    public Object getMembers(UUID orgId) {
        System.out.println("Inside members fetching method");
        try {
            String url = dbUrl+"/db/api/org/users/"+orgId;
            ResponseEntity response = makeGetCall(url,"",new HashMap<>());
            System.out.println("users fetch successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed Fetching users");
            throw new RuntimeException("failed Fetching users");
        }
    }

    public Object addMemberRole(UUID subProjectId, UUID userId, UUID roleId) {
        System.out.println("Inside add role creating method");
        try {
            String endpoint = dbUrl+"/db/api/user/add/role/"+subProjectId+"/"+userId+"/"+roleId;
            ResponseEntity response = makePostCall(null,endpoint,"",new HashMap<>());
            System.out.println("roll added successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed adding role");
            throw new RuntimeException("failed adding role");
        }
    }

    public Object getSubProjectMembers(UUID subProjectId) {
        System.out.println("Inside members fetching method");
        try {
            String url = dbUrl+"/db/api/subProject/users/"+subProjectId;
            ResponseEntity response = makeGetCall(url,"",new HashMap<>());
            System.out.println("users fetch successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed Fetching users");
            throw new RuntimeException("failed Fetching users");
        }
    }

    public Object createStatus(UUID subProjectId, String statusName) {
        System.out.println("Inside status creating method");
        try {
            String endpoint = dbUrl+"/db/api/status/create/"+subProjectId;
            StatusRequestDTO requestDTO = new StatusRequestDTO();
            requestDTO.setName(statusName);
            ResponseEntity response = makePostCall(requestDTO,endpoint,"",new HashMap<>());
            System.out.println("created status successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed creating status");
            throw new RuntimeException("failed creating status");
        }
    }

    public Object getStatus(UUID subProjectId) {
        System.out.println("Inside status fetching method");
        try {
            String url = dbUrl+"/db/api/status/get/"+subProjectId;
            ResponseEntity response = makeGetCall(url,"",new HashMap<>());
            System.out.println("users fetch successfully");
            return response.getBody();
        }catch (Exception e){
            System.out.println("failed Fetching users");
            throw new RuntimeException("failed Fetching users");
        }
    }
}
