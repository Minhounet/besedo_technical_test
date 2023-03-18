package com.qmt.besedo.model.response;

import java.util.List;

public record ErrorResponse(String error, List<String> causes) implements Response {

}
