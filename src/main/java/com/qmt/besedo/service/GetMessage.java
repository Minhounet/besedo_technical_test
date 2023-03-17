package com.qmt.besedo.service;

import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.Response;
import org.springframework.http.ResponseEntity;

public interface GetMessage {
    ResponseEntity<Response> getMessages(String attribute, SearchOperator searchOperator, String value);
}
