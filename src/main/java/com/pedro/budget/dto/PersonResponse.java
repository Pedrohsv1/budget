package com.pedro.budget.dto;

import java.util.UUID;

import lombok.Value;

@Value
public class PersonResponse {
    private UUID id;
    private String name;
    private String email;
}
