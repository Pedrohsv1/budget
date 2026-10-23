package com.pedro.budget.dto;

import java.util.List;
import java.util.UUID;

import com.pedro.budget.entity.Person;

public record PersonResponse(
        UUID id,
        String name,
        String email) {
    public static PersonResponse from(UUID id, String name, String email) {
        return new PersonResponse(id, name, email);
    }

    public static List<PersonResponse> fromList(List<Person> persons) {
        return persons.stream()
                .map(personResponse -> new PersonResponse(
                        personResponse.getId(),
                        personResponse.getName(),
                        personResponse.getEmail()))
                .toList();
    }
}