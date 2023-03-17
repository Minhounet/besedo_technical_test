package com.qmt.besedo.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public final class SuccessResponseWithResults<T> extends Response {
    private final String message;
    private final List<T> results;
}
