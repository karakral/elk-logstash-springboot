package com.arman.elk.rest;


import com.arman.elk.dto.SourceDocument;
import com.arman.elk.service.ElasticsearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("elk/")
public class LogController {

    private final ElasticsearchService elasticsearchService;

    @Autowired
    public LogController(ElasticsearchService elasticsearchService) {
        this.elasticsearchService = elasticsearchService;
    }


    @GetMapping("/search-logs")
    public ResponseEntity<?> searchLogsStructured(@RequestParam String action) {
            List<SourceDocument> list = elasticsearchService.searchLogsByAction(action);
        return ResponseEntity.ok(list);

    }
}