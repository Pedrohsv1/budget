package com.pedro.budget.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pedro.budget.dto.PersonRequest;
import com.pedro.budget.dto.PersonResponse;
import com.pedro.budget.entity.Person;
import com.pedro.budget.entity.User;
import com.pedro.budget.repository.PersonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;

    public PersonResponse createPerson(PersonRequest request, User user) {
        Person person = personRepository.save(request.toEntity(user));
        return PersonResponse.from(person.getId(), person.getName(), person.getEmail());
    }

    public List<PersonResponse> listPersons(User user) {
        return PersonResponse.fromList(personRepository.findByUserId(user.getId()));
    }
}
