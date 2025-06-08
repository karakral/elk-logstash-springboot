package com.arman.elk.service;


import com.arman.elk.dto.SourceDocument;

import java.util.List;

public interface SearchLogsByKey {

    List<SourceDocument> searchLogsByAction(String action, String size) throws Exception;
  //  List<SourceDocument> searchLogsByNationalId(String nationalId, String size) throws Exception;

}
