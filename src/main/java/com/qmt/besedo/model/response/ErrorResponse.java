package com.qmt.besedo.model.response;

import java.util.List;

/**
 * An error {@link Response} with an error message with a list of causes which can be empty.
 * @param error
 * @param causes
 */
public record ErrorResponse(String error, List<String> causes) implements Response {

}
