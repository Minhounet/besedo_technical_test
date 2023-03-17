package com.qmt.besedo.response;

import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.model.response.SuccessResponse;
import io.vavr.control.Try;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Log4j2
@UtilityClass
public class Responses {

    public static <E> ResponseEntity<Response> buildResponseFromExecution(String successMessage,
                                                                   HttpStatus successStatus,
                                                                   String errorMessage,
                                                                   HttpStatus errorStatus,
                                                                   Try<E> block) {
        var effectiveErrorMessage = null == errorMessage || errorMessage.isBlank() ? "Unexpected error" : errorMessage;
        block.onFailure(cause -> log.error(effectiveErrorMessage, cause));
        return block.fold(cause -> ResponseEntity
                        .status(errorStatus)
                        .body(new ErrorResponse(effectiveErrorMessage, List.of())),
                mess -> ResponseEntity
                        .status(successStatus)
                        .body(new SuccessResponse(successMessage)));
    }
}
