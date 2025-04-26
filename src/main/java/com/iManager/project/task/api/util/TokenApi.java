package com.iManager.project.task.api.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Collections;
import java.util.HashMap;

@Service
public class TokenApi {

    @Autowired
    RestTemplate restTemplate;

    @Value("${service.main.url}")
    String mainApi;

    public ResponseEntity tokenVerify(String authHeader) {
        String url = mainApi+"/api/v1/token/verify";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization",authHeader);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,entity,String.class);
            return response;
        }catch (Exception e){
            throw e;
        }
    }
}
