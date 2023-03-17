package com.qmt.besedo.response;

import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.model.response.SuccessResponse;
import com.qmt.besedo.model.response.SuccessResponseWithResults;
import io.vavr.control.Try;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.Function;

@Log4j2
@UtilityClass
public class Responses {

    private static final Function<String, String> GET_ERROR_MESSAGE = error -> null == error || error.isBlank() ? "Unexpected error" : error;

    public static <E> ResponseEntity<Response> buildResponseFromExecution(String successMessage,
                                                                   HttpStatus successStatus,
                                                                   String errorMessage,
                                                                   HttpStatus errorStatus,
                                                                   Try<E> block) {
        var effectiveErrorMessage = GET_ERROR_MESSAGE.apply(errorMessage);
        block.onFailure(cause -> log.error(effectiveErrorMessage, cause));
        return block.fold(cause -> ResponseEntity
                        .status(errorStatus)
                        .body(new ErrorResponse(effectiveErrorMessage, List.of())),
                mess -> ResponseEntity
                        .status(successStatus)
                        .body(new SuccessResponse(successMessage)));
    }

    public static <E> ResponseEntity<Response> buildResponseWithResultsFromExecution(String successMessage,
                                                                          HttpStatus successStatus,
                                                                          String errorMessage,
                                                                          HttpStatus errorStatus,
                                                                          Try<List<E>> block) {
        var effectiveErrorMessage = GET_ERROR_MESSAGE.apply(errorMessage);
        block.onFailure(cause -> log.error(effectiveErrorMessage, cause));
        return block.fold(cause -> ResponseEntity
                        .status(errorStatus)
                        .body(new ErrorResponse(effectiveErrorMessage, List.of())),
                mess -> ResponseEntity
                        .status(successStatus)
                        .body(new SuccessResponseWithResults<>(successMessage, mess)));
    }


}
