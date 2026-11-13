package com.pedro.budget.dto;

import com.pedro.budget.entity.Transaction.Transaction;
import com.pedro.budget.entity.Transaction.TransactionCredit;
import com.pedro.budget.enums.TransactionType;
import jakarta.persistence.criteria.*;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public record SearchTransactionsParams (
        String q,
        Integer year,
        Integer month,
        TransactionType type,
        List<UUID> categoriesId,
        List<UUID> excludedCategoriesId,
        List<UUID> banksId,
        List<UUID> excludedBankId,
        List<UUID> personsId,
        List<UUID> excludedPersonId,
        List<UUID> cardId,
        List<UUID> excludedCardId,
        Long minAmount,
        Long maxAmount
) implements Specification<Transaction> {

    @Override
    public @Nullable Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        Expression<Integer> yearExpression = criteriaBuilder.function(
                "date_part",
                Integer.class,
                criteriaBuilder.literal("year"),
                root.get("date")
        );

        Expression<Integer> monthExpression = criteriaBuilder.function(
                "date_part",
                Integer.class,
                criteriaBuilder.literal("month"),
                root.get("date")
        );

        if(q != null && !q.trim().isBlank()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + this.q.trim().toLowerCase() + "%"));
        }

        if (this.year != null) {
            predicates.add(criteriaBuilder.equal(yearExpression, this.year));
        }

        if (this.month != null) {
            predicates.add(criteriaBuilder.equal(monthExpression, this.month));
        }

        if (this.type != null) {
            predicates.add(criteriaBuilder.equal(root.get("transactionType"), this.type));
        }

        if (this.categoriesId != null && !this.categoriesId.isEmpty()) {
            predicates.add(root.get("category").get("id").in(this.categoriesId));
        }

        if (this.excludedCategoriesId != null && !this.excludedCategoriesId.isEmpty()) {
            predicates.add(criteriaBuilder.not(root.get("category").get("id").in(this.excludedCategoriesId)));
        }

        if (this.banksId != null && !this.banksId.isEmpty()) {
            predicates.add(root.get("bankAccount").get("id").in(this.banksId));
        }

        if (this.excludedBankId != null && !this.excludedBankId.isEmpty()) {
            predicates.add(criteriaBuilder.not(root.get("bankAccount").get("id").in(this.excludedBankId)));
        }

        if (this.personsId != null && !this.personsId.isEmpty()) {
            predicates.add(root.get("person").get("id").in(this.personsId));
        }

        if (this.excludedPersonId != null && !this.excludedPersonId.isEmpty()) {
            predicates.add(criteriaBuilder.not(root.get("person").get("id").in(this.excludedPersonId)));
        }

        if (this.cardId != null && !this.cardId.isEmpty()) {
            Subquery<UUID> subquery = query.subquery(UUID.class);
            Root<TransactionCredit> creditSub = subquery.from(TransactionCredit.class);
            subquery.select(creditSub.get("id")).where(creditSub.get("purchase").get("card").get("id").in(cardId));

            predicates.add(root.get("id").in(subquery));
        }

        if (this.excludedCardId != null && !this.excludedCardId.isEmpty()) {
            Subquery<UUID> subquery = query.subquery(UUID.class);
            Root<TransactionCredit> creditSub = subquery.from(TransactionCredit.class);
            subquery.select(creditSub.get("id")).where(creditSub.get("purchase").get("card").get("id").in(excludedCardId()));

            predicates.add(criteriaBuilder.not(root.get("id").in(subquery)));
        }

        if (this.minAmount != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("amount"), this.minAmount));
        }

        if (this.maxAmount != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("amount"), this.maxAmount));
        }

        return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
    }
}
