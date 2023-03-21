package com.qmt.besedo.model.response;

import java.util.List;

/**
 * A success {@link Response} with a success message and a {@link List} or results.
 * @param message
 * @param results
 * @param <T>
 */
public record SuccessResponseWithResults<T>(String message, List<T> results) implements Response {
}
