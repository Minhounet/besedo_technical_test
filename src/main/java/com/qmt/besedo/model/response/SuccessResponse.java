package com.qmt.besedo.model.response;

/**
 * {@link Response} with a success message
 * @param message the success message
 */
public record SuccessResponse(String message) implements Response {

}
