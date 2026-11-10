package com.pedro.budget.controller;

import com.pedro.budget.dto.GlobalSearchResponse;
import com.pedro.budget.entity.User;
import com.pedro.budget.service.SearchService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    @GetMapping()
    public ResponseEntity<GlobalSearchResponse> search(@RequestParam @Size(min = 2) String q, @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.OK).body(searchService.GlobalSearch(user, q));
    }
}
