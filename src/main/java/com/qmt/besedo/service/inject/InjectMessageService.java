package com.qmt.besedo.service.inject;

import com.qmt.besedo.model.message.Message;
import com.qmt.besedo.model.response.Response;
import org.springframework.http.ResponseEntity;

/**
 * Service to create {@link Message} into database.
 */
public interface InjectMessageService {

    /**
     *
     * @param message the {@link Message} to add to the database
     * @return Response with creation confirmation message in case of success.
     */
    ResponseEntity<Response> inject(Message message);
}
