package com.qmt.besedo.response;

import io.vavr.control.Try;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Log4j2
@UtilityClass
public class Responses {

    public <E> ResponseEntity<String> executeAndBuildResponse(String errorMessage, Try<E> block) {
        var effectiveErrorMessage = null == errorMessage || errorMessage.isBlank() ? "Unexpected error" : errorMessage;
        block.onFailure(cause -> log.error(effectiveErrorMessage, cause));
        return block.fold(cause -> ResponseEntity.internalServerError().body(effectiveErrorMessage),
                mess -> ResponseEntity.status(HttpStatus.CREATED).build());
    }
}
