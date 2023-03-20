package com.qmt.besedo.service.search;

import com.qmt.besedo.model.message.MessageAttributeName;
import com.qmt.besedo.model.operator.SearchOperator;
import com.qmt.besedo.model.response.Response;
import org.springframework.http.ResponseEntity;

/**
 * Service to search messages in database.
 */
public interface SearchMessageService {

    /**
     * <p>Get all {@link com.qmt.besedo.model.message.Message}s matching the filter conditions on attribute from the {@link com.qmt.besedo.model.message.Message}</p>
     * <p>filterAttributeName can only be equals to "id", "mail", "title" or "body". If not  {@link Response} with error is raised to the user.</p>
     * @param attributeName the {@link MessageAttributeName}
     * @param searchOperator a {@link SearchOperator}
     * @param filterValue any value to match
     * @return ResponseEntity with the list of {@link com.qmt.besedo.model.message.Message}.
     */
    ResponseEntity<Response> getMessages(MessageAttributeName attributeName, SearchOperator searchOperator, String filterValue);
}
