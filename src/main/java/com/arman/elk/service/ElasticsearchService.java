package com.arman.elk.service;

import com.arman.elk.dto.ElasticsearchResponse;
import com.arman.elk.dto.Hit;
import com.arman.elk.dto.SourceDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ElasticsearchService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${elasticsearch.search.url}")
    private String elasticsearchUrl;


    @Autowired
    public ElasticsearchService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }


    public  List<SourceDocument> searchLogsByAction(String actionValue) {
        ElasticsearchResponse esResponse = searchLogsByAction2(actionValue);
        List<SourceDocument> logDocuments = new ArrayList<>();
        try {
        if (esResponse != null && esResponse.getHits() != null && esResponse.getHits().getHits() != null) {
           logDocuments = esResponse.getHits().getHits().stream()
                    .map(Hit::getSource)
                    .collect(Collectors.toList());
            logDocuments.forEach(doc -> System.out.println("Trace ID: " + doc.getTraceId() + ", Action: " + doc.getAction()));
        }
        //return ResponseEntity.ok("No hits found or empty response.");
    } catch (Exception e){
        //return ResponseEntity.status(500).body("Error processing search: " + e.getMessage());
        }
        return logDocuments;

    }

    public ElasticsearchResponse searchLogsByAction2(String actionValue) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> queryWrapper = Map.of(
                "query", Map.of(
                        "match", Map.of(
                                "action", actionValue
                        )
                )
        );

        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(queryWrapper);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating JSON request body", e);
        }

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<ElasticsearchResponse> response =
                    restTemplate.exchange(elasticsearchUrl, HttpMethod.POST, entity, ElasticsearchResponse.class);
            return response.getBody();
        } catch (Exception e) {
            System.err.println("Error during Elasticsearch request: " + e.getMessage());
            throw new RuntimeException("Generic error during Elasticsearch request: " + e.getMessage(), e);
        }
    }
}