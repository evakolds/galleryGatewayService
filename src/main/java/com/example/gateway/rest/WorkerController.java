package com.example.gateway.rest;

import com.example.gateway.rest.dto.Worker;
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
@RequestMapping("/worker")
@NoArgsConstructor
public class WorkerController {

    private final String url = "http://gallery-workers:8085/worker";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${worker.routing-key}")
    private String routingKey;

    @PostMapping
    public Worker createWorker(@RequestBody Worker worker) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Worker> result =
                restTemplate.postForEntity(url, worker, Worker.class);
        return result.getBody();
    }

    @PostMapping("mq")
    public String createWorkerMq(@RequestBody Worker worker) {
        rabbitTemplate.convertAndSend(exchange, routingKey, worker);
        return "Sent successfully";
    }

    @GetMapping
    public List<Worker> getAllWorkers() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Worker>> result =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        return result.getBody();
    }

    @GetMapping("{id}")
    public Worker getById(@PathVariable(value = "id") UUID id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Worker> result =
                restTemplate.exchange(url + "/" + id.toString(),
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        return result.getBody();
    }

}
