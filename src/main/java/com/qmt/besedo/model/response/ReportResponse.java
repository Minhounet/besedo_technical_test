package com.qmt.besedo.model.response;

import java.util.UUID;

public record ReportResponse(UUID requestId, boolean inProcess) implements Response {
}
