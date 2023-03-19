package com.qmt.besedo.response;

import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.model.response.SuccessResponse;
import com.qmt.besedo.model.response.SuccessResponseWithResults;
import com.qmt.besedo.utility.Strings;
import io.vavr.control.Try;
import lombok.experimental.UtilityClass;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.function.Function;

/**
 * Utility class for {@link Response} objects.
 */
@Log4j2
@UtilityClass
public class Responses {

    /**
     * Build {@link ResponseEntity} with a {@link Response} handling success or error in the block execution.
     *
     * @param successMessage the message in case of success
     * @param successStatus  the status in case of success
     * @param errorMessage   the message in case of error
     * @param errorStatus    the status in case or error
     * @param block          the {@link Try} execution
     * @param <E>            the returning type in the block execution. Results is not used.
     * @return {@link ResponseEntity} with a {@link Response}
     */
    public static <E> ResponseEntity<Response> buildNoResultResponseFromExecution(String successMessage,
                                                                                  HttpStatus successStatus,
                                                                                  String errorMessage,
                                                                                  HttpStatus errorStatus,
                                                                                  Try<E> block) {
        return buildResponseFromExecution(errorMessage, errorStatus, block, mess -> ResponseEntity
                .status(successStatus)
                .body(new SuccessResponse(successMessage)));
    }



    /**
     * Build {@link ResponseEntity} with a {@link Response} handling success or error in the block execution. List of results
     * is included in the {@link ResponseEntity}
     *
     * @param successMessage the message in case of success
     * @param successStatus  the status in case of success
     * @param errorMessage   the message in case of error
     * @param errorStatus    the status in case or error
     * @param block          the {@link Try} execution
     * @param <E>            the returning type in the block execution.
     * @return {@link ResponseEntity} with a {@link Response} including a List of results
     */
    public static <E> ResponseEntity<Response> buildResponseWithResultsFromExecution(String successMessage,
                                                                                     HttpStatus successStatus,
                                                                                     String errorMessage,
                                                                                     HttpStatus errorStatus,
                                                                                     Try<List<E>> block) {
        return buildResponseFromExecution(errorMessage, errorStatus, block, mess -> ResponseEntity
                .status(successStatus)
                .body(new SuccessResponseWithResults<>(successMessage, mess)));
    }

    private static <E> ResponseEntity<Response> buildResponseFromExecution(String errorMessage,
                                                                           HttpStatus errorStatus,
                                                                           Try<E> block,
                                                                           Function<E, ResponseEntity<Response>> successCallback) {
        var effectiveErrorMessage = Strings.BUILD_ERROR_MESSAGE.apply(errorMessage);
        block.onFailure(cause -> log.error(effectiveErrorMessage, cause));
        return block.fold(cause -> ResponseEntity
                        .status(errorStatus)
                        .body(new ErrorResponse(effectiveErrorMessage, List.of())),
                successCallback);
    }

}
