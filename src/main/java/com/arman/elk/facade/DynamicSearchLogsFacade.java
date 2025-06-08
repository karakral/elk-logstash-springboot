package com.arman.elk.facade;

import com.arman.elk.dto.SourceDocument;
import com.arman.elk.service.DynamicSearchLogs;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DynamicSearchLogsFacade {


    private final DynamicSearchLogs dynamicSearchLogs;

    public DynamicSearchLogsFacade(DynamicSearchLogs dynamicSearchLogs) {
        this.dynamicSearchLogs = dynamicSearchLogs;
    }

    public List<SourceDocument> searchLogsByAction(String action, String size) throws  Exception {
        return dynamicSearchLogs.searchLogsByAction(action, size);
    }
}
