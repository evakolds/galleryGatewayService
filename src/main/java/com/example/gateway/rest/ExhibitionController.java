package com.example.gateway.rest;

import com.example.gateway.rest.dto.Exhibition;
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
@RequestMapping("/exhibition")
@NoArgsConstructor
public class ExhibitionController {

    private final String url = "http://gallery-exhibitions:8081/exhibition";

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange}")
    private String exchange;
    @Value("${film.routing-key}")
    private String routingKey;

    @PostMapping
    public Exhibition createExhibition(@RequestBody Exhibition exhibition) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Exhibition> result =
                restTemplate.postForEntity(url, exhibition, Exhibition.class);
        return result.getBody();
    }

    @PostMapping("mq")
    public String createExhibitionMq(@RequestBody Exhibition exhibition) {
        rabbitTemplate.convertAndSend(exchange, routingKey, exhibition);
        return "Sent successfully";
    }

    @GetMapping
    public List<Exhibition> getAllExhibitions() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Exhibition>> result =
                restTemplate.exchange(url, HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        return result.getBody();
    }

    @GetMapping("{id}")
    public Exhibition getById(@PathVariable(value = "id") UUID id) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Exhibition> result =
                restTemplate.exchange(url + "/" + id.toString(),
                        HttpMethod.GET, null,
                        new ParameterizedTypeReference<>() {
                        });
        return result.getBody();
    }

}
