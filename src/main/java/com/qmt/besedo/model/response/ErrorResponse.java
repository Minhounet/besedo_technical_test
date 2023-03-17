package com.qmt.besedo.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public final class ErrorResponse extends Response {

    private final String error;
    private final List<String> causes;


}
