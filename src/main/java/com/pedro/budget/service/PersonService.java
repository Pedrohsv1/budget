package com.pedro.budget.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pedro.budget.dto.PersonRequest;
import com.pedro.budget.dto.PersonResponse;
import com.pedro.budget.entity.User;
import com.pedro.budget.mapper.PersonMapper;
import com.pedro.budget.repository.PersonRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PersonService {
    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public PersonResponse createPerson(PersonRequest request, User user) {
        return personMapper.toResponse(personRepository.save(personMapper.toEntity(request, user)));
    }

    public List<PersonResponse> listPersons(User user) {
        return personMapper.toListResponse(personRepository.findByUserId(user.getId()));
    }
}
