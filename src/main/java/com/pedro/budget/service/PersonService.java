package com.pedro.budget.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pedro.budget.dto.PersonRequest;
import com.pedro.budget.dto.PersonResponse;
import com.pedro.budget.entity.Person;
import com.pedro.budget.entity.User;
import com.pedro.budget.repository.PersonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    @Transactional
    public PersonResponse createPerson(PersonRequest request, User user) {
        if (personRepository.findByNameAndUserId(request.name(), user.getId()).isPresent()) {
            throw new RuntimeException("This name is already used by a person");
        }

        Person person = personRepository.save(request.toEntity(user));
        return PersonResponse.from(person);
    }

    public Page<PersonResponse> listPersons(User user, Pageable pageable) {
        return personRepository.findByUserId(user.getId(), pageable)
                .map(PersonResponse::from);
    }

    @Transactional
    public PersonResponse updatePerson(UUID id, PersonRequest request, User user) {
        Person person = personRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Person not found"));
        person.update(request.name(), request.email());
        personRepository.save(person);
        return PersonResponse.from(person);
    }

    @Transactional
    public void deletePerson(UUID id, User user) {
        Person person = personRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new RuntimeException("Person not found"));
        personRepository.delete(person);
    }

    @Transactional
    public Void createDefaultPerson(User user) {
        Person defaultPerson = new Person("Me", user.getUsername(), user);
        personRepository.save(defaultPerson);
        return null;
    }
}
