package com.example.gateway.rest;

import com.example.gateway.rest.dto.Visitor;
import lombok.NoArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${visitor.routing-key}")
    private String routingKey;

    @PostMapping
    public Visitor createVisitor(@RequestBody Visitor visitor) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Visitor> result =
                restTemplate.postForEntity(url, visitor, Visitor.class);
        return result.getBody();
    }

    @PostMapping("mq")
    public String createVisitorMq(@RequestBody Visitor visitor) {
        rabbitTemplate.convertAndSend(exchange, routingKey, visitor);
        return "Sent successfully";
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
