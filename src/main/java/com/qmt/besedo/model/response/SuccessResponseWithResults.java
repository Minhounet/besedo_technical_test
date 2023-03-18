package com.qmt.besedo.model.response;

import java.util.List;

public record SuccessResponseWithResults<T>(String message, List<T> results) implements Response {
}
