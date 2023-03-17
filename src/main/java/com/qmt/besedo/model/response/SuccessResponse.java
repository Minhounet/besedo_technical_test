package com.qmt.besedo.model.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public final class SuccessResponse extends Response {

    private final String message;
}
