package com.example.gateway.api;

import com.example.gateway.dto.Visitor;
import lombok.NoArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/visitor")
@NoArgsConstructor
public class VisitorController {

    private final String url = "http://gallery-visitors:8084/visitor";

    @PostMapping
    public Visitor createVisitor(@RequestBody Visitor visitor) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Visitor> result =
                restTemplate.postForEntity(url, visitor, Visitor.class);
        return result.getBody();
    }

    @GetMapping
    public List<Visitor> getAllVisitors() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Visitor>> result =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        return result.getBody();
    }

    @GetMapping("{id}")
    public Visitor getById(@PathVariable(value = "id") UUID id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Visitor> result =
                restTemplate.exchange(url + "/" + id.toString(),
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        return result.getBody();
    }
    
}
