package com.pedro.budget.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return PersonResponse.from(person);
    }

    public Page<PersonResponse> listPersons(User user, Pageable pageable) {
        return personRepository.findByUserId(user.getId(), pageable)
                .map(PersonResponse::from);
    }

    public PersonResponse updatePerson(Long id, PersonRequest request, User user) {
        Person person = personRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.setName(request.name());
        person.setEmail(request.email());
        personRepository.save(person);
        return PersonResponse.from(person);
    }

    public void deletePerson(Long id, User user) {
        Person person = personRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Person not found"));
        personRepository.delete(person);
    }
}
