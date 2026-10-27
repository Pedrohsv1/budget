package com.pedro.budget.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pedro.budget.dto.PersonRequest;
import com.pedro.budget.dto.PersonResponse;
import com.pedro.budget.dto.validation.OnCreate;
import com.pedro.budget.dto.validation.OnUpdate;
import com.pedro.budget.entity.User;
import com.pedro.budget.service.PersonService;

import lombok.AllArgsConstructor;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
    private final PersonService personService;

    @PostMapping()
    public ResponseEntity<PersonResponse> createPerson(@Validated(OnCreate.class) @RequestBody PersonRequest request,
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

    @PutMapping("/{id}")
    public ResponseEntity<PersonResponse> updatePerson(@PathVariable UUID id,
            @Validated(OnUpdate.class) @RequestBody PersonRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(personService.updatePerson(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        personService.deletePerson(id, user);
        return ResponseEntity.noContent().build();
    }
}
