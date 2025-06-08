package com.arman.elk.rest;


import com.arman.elk.dto.SourceDocument;
import com.arman.elk.facade.DynamicSearchLogsFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("elk/")
public class LogController {

    private final DynamicSearchLogsFacade dynamicSearchLogsFacade;

    public LogController(DynamicSearchLogsFacade dynamicSearchLogsFacade) {
        this.dynamicSearchLogsFacade = dynamicSearchLogsFacade;
    }

    @GetMapping("search-logs")
    public ResponseEntity<?> searchLogsStructured(@RequestParam String action, String size) throws Exception {
            List<SourceDocument> list = dynamicSearchLogsFacade.searchLogsByAction (action, size);
        return ResponseEntity.ok(list);
    }

//    @GetMapping("search-logs/national-id")
//    //TODO Mahsun add validation
//    public ResponseEntity<?> searchLogsByNationalId(@RequestParam String nationalId,  String size) throws Exception {
//        List<SourceDocument> list = searchLogsByAction.searchLogsByNationalId(nationalId, size);
//        return ResponseEntity.ok(list);
//    }
}