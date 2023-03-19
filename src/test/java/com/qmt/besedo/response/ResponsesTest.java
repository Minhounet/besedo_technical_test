package com.qmt.besedo.response;

import com.qmt.besedo.model.response.ErrorResponse;
import com.qmt.besedo.model.response.Response;
import com.qmt.besedo.model.response.SuccessResponse;
import com.qmt.besedo.model.response.SuccessResponseWithResults;
import io.vavr.control.Try;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static com.qmt.besedo.response.Responses.buildNoResultResponseFromExecution;
import static com.qmt.besedo.response.Responses.buildResponseWithResultsFromExecution;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ResponsesTest {


    @Test
    void buildNoResultResponseFromExecution_WITH_error_during_execution_EXPECTED_response_with_provided_error_message() {
        ResponseEntity<Response> expected = ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(new ErrorResponse("Error in my life", List.of()));

        ResponseEntity<Response> actual = buildNoResultResponseFromExecution(null,
                null,
                "Error in my life",
                HttpStatus.BAD_GATEWAY,
                Try.failure(new RuntimeException()));

        assertEquals(expected, actual);
    }

    @Test
    void buildResponseWithResultsFromExecution_WITH_error_during_execution_EXPECTED_response_with_provided_error_message() {
        ResponseEntity<Response> expected = ResponseEntity
                .status(HttpStatus.BAD_GATEWAY)
                .body(new ErrorResponse("Error in my life", List.of()));

        ResponseEntity<Response> actual = buildResponseWithResultsFromExecution(null,
                null,
                "Error in my life",
                HttpStatus.BAD_GATEWAY,
                Try.failure(new RuntimeException()));

        assertEquals(expected, actual);
    }

    @Test
    void buildNoResultResponseFromExecution_WITH_success_execution_EXPECTED_SuccessResponse() {
        ResponseEntity<Response> expected = ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponse("success is key"));


        ResponseEntity<Response> actual = buildNoResultResponseFromExecution("success is key",
                HttpStatus.OK,
                "Error in my life",
                HttpStatus.BAD_GATEWAY,
                Try.success("success"));

        assertEquals(expected, actual);
    }

    @Test
    void buildResponseWithResultsFromExecution_WITH_success_execution_EXPECTED_SuccessResponse() {
        ResponseEntity<Response> expected = ResponseEntity
                .status(HttpStatus.OK)
                .body(new SuccessResponseWithResults<>("success is key", List.of("success")));


        ResponseEntity<Response> actual = buildResponseWithResultsFromExecution("success is key",
                HttpStatus.OK,
                "Error in my life",
                HttpStatus.BAD_GATEWAY,
                Try.success(List.of("success")));

        assertEquals(expected, actual);
    }
}