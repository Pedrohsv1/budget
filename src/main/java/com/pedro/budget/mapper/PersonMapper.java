package com.pedro.budget.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pedro.budget.dto.PersonRequest;
import com.pedro.budget.dto.PersonResponse;
import com.pedro.budget.entity.Person;
import com.pedro.budget.entity.User;

@Component
public class PersonMapper {
    public Person toEntity(PersonRequest request, User user) {
        Person person = new Person();

        person.setName(request.getName());
        person.setEmail(request.getEmail());
        person.setUser(user);

        return person;
    }

    public PersonResponse toResponse(Person entity) {
        return new PersonResponse(entity.getId(), entity.getName(), entity.getEmail());
    }

    public List<PersonResponse> toListResponse(List<Person> entities) {
        if (entities.isEmpty() || entities == null) {
            return new ArrayList<>();
        }

        return entities.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
