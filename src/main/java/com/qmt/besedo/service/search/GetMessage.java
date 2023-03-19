package com.qmt.besedo.service.search;

import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.Response;
import org.springframework.http.ResponseEntity;

/**
 * Service to search messages in database.
 */
public interface GetMessage {

    /**
     * <p>Get all {@link com.qmt.besedo.model.message.Message}s matching the filter conditions on attribute from the {@link com.qmt.besedo.model.message.Message}</p>
     * <p>filterAttributeName can only be equals to "id", "mail", "title" or "body". If not  {@link Response} with error is raised to the user.</p>
     * @param filterAttributeName value between "id", "mail", "title" or "body"
     * @param searchOperator a {@link SearchOperator}
     * @param filterValue any value to match
     * @return ResponseEntity with the list of {@link com.qmt.besedo.model.message.Message}.
     */
    ResponseEntity<Response> getMessages(String filterAttributeName, SearchOperator searchOperator, String filterValue);
}
