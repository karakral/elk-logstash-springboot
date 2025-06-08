package com.arman.elk.service;

import com.arman.elk.dto.ElasticsearchResponse;
import com.arman.elk.dto.Hit;
import com.arman.elk.dto.SourceDocument;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DynamicSearchLogs implements SearchLogsByKey {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${elasticsearch.search.url}")
    private String elasticsearchUrl;


    @Autowired
    public DynamicSearchLogs(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Override
    public List<SourceDocument> searchLogsByAction(String action, String size) throws Exception {
        ElasticsearchResponse elasticResponse = callLogsByAction(action, size);
        return convertElkToResponse(elasticResponse);
    }

//    @Override
//    public List<SourceDocument> searchLogsByNationalId(String nationalId, String size) throws Exception {
//        ElasticsearchResponse elasticResponse = callLogsByNationalId(nationalId, size);
//        return convertElkToResponse(elasticResponse);
//    }



    //-------------------------------

    public ElasticsearchResponse callLogsByAction(String action, String size) {
        return callElk(createRequestBody(action, size));
    }

//    public ElasticsearchResponse callLogsByNationalId(String nationalId, String size) {
//        return callElk(createQueryByNationalId(nationalId, size));
//    }

    private String createRequestBody(String action, String size) {


        String queryParameter = recognazationQueryType(action);

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        Map<String, Object> matchMap = new HashMap<>();
        matchMap.put(queryParameter, action);
        queryMap.put("match", matchMap);
        requestBody.put("query", queryMap);
        requestBody.put("size", size);
        Map<String, Object> sortOrderDetails = new HashMap<>();
        sortOrderDetails.put("order", "desc");
        Map<String, Object> sortFieldConfig = new HashMap<>();
        sortFieldConfig.put("timestamp", sortOrderDetails);
        List<Map<String, Object>> sortList = new ArrayList<>();
        sortList.add(sortFieldConfig);
        requestBody.put("sort", sortList);
        return createRequestBodyBy(requestBody);
    }


//    private String createQueryByNationalId(String nationalId, String size) {
//        Map<String, Object> requestBody = new HashMap<>();
//        Map<String, Object> queryMap = new HashMap<>();
//        Map<String, Object> matchMap = new HashMap<>();
//        matchMap.put("nationalId", nationalId);
//        queryMap.put("match", matchMap);
//        requestBody.put("query", queryMap);
//        requestBody.put("size", size);
//        Map<String, Object> sortOrderDetails = new HashMap<>();
//        sortOrderDetails.put("order", "desc");
//        Map<String, Object> sortFieldConfig = new HashMap<>();
//        sortFieldConfig.put("timestamp", sortOrderDetails);
//        List<Map<String, Object>> sortList = new ArrayList<>();
//        sortList.add(sortFieldConfig);
//        requestBody.put("sort", sortList);
//        return createRequestBodyBy(requestBody);
//    }

    private String createRequestBodyBy(Map<String, Object> queryWrapper) {
        String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(queryWrapper);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating JSON request body", e);
        }
        return requestBody;
    }

    private ElasticsearchResponse callElk(String requestBody) {
        HttpEntity<String> entity = initializeRequestBody(requestBody);
        try {
            ResponseEntity<String> response =
                    restTemplate.exchange(elasticsearchUrl, HttpMethod.POST, entity, String.class);
            System.out.println(response.getBody());
            ElasticsearchResponse elasticsearchResponse = objectMapper.readValue(response.getBody(), ElasticsearchResponse.class);
            return elasticsearchResponse;
        } catch (Exception e) {
            System.err.println("Error during Elasticsearch request: " + e.getMessage());
            throw new RuntimeException("Generic error during Elasticsearch request: " + e.getMessage(), e);
        }
    }

    private HttpEntity<String> initializeRequestBody(String requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build the query body as a Map


        return new HttpEntity<>(requestBody, headers);
    }


    private List<SourceDocument> convertElkToResponse(ElasticsearchResponse elasticResponse) throws Exception {
        List<SourceDocument> logDocuments = new ArrayList<>();
        try {
            if (elasticResponse != null && elasticResponse.getHits() != null && elasticResponse.getHits().getHits() != null) {
                logDocuments = elasticResponse.getHits().getHits().stream()
                        .map(Hit::getSource)
                        .collect(Collectors.toList());
                logDocuments.forEach(doc -> System.out.println("Trace ID: " + doc.getTraceId() + ", Action: " + doc.getAction()));
            }
        } catch (Exception e) {
            throw new Exception();
        }
        return logDocuments;
    }


    private String recognazationQueryType(String action) {
        String queryAction = "";

        switch (action.toUpperCase()) {
            case "CREATE":
            case "CREATE2":
            case "POST":
            case "UPDATE":
            case "DELETE":
            queryAction = "action";
        }
        if (queryAction.equals("")) {
            boolean isNationalId = nationalIdValidation(action);
            if (isNationalId) {
                queryAction = "nationalId";
            }
        }
        return queryAction;
    }



    public boolean nationalIdValidation(String nationalId) {

        String[] identicalDigits = {"0000000000", "1111111111", "2222222222", "3333333333", "4444444444", "5555555555", "6666666666", "7777777777", "8888888888", "9999999999"};

        if (nationalId.trim().isEmpty()) {
            return false;
        } else if (nationalId.length() != 10) {
            return false;
        } else if (Arrays.asList(identicalDigits).contains(nationalId)) {
            return false;
        } else {
            int sum = 0;

            for (int i = 0; i < 9; i++) {
                sum += Character.getNumericValue(nationalId.charAt(i)) * (10 - i);
            }

            int lastDigit;
            int divideRemaining = sum % 11;

            if (divideRemaining < 2) {
                lastDigit = divideRemaining;
            } else {
                lastDigit = 11 - (divideRemaining);
            }

            if (Character.getNumericValue(nationalId.charAt(9)) == lastDigit) {
                return true;
            } else {
                return false;
            }
        }
    }
}