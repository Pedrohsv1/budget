package com.pedro.budget.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.budget.dto.PersonRequest;
import com.pedro.budget.dto.PersonResponse;
import com.pedro.budget.entity.User;
import com.pedro.budget.service.PersonService;

import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping()
    public ResponseEntity<PersonResponse> createPerson(@RequestBody PersonRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(personService.createPerson(request, user));
    }

    @GetMapping()
    public ResponseEntity<Page<PersonResponse>> listPersons(@AuthenticationPrincipal User user,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size) {
        PageRequest pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(personService.listPersons(user, pageable));
    }
}
