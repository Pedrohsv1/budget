package com.pedro.budget.exception;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public record ApiErrors(
        LocalDateTime timestamp,
        int status,
        String message,
        String path,
        List<Map<String, String>> erros) {
}
