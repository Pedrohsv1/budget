package com.pedro.budget.repository.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import aj.org.objectweb.asm.commons.Remapper;
import com.pedro.budget.dto.SearchTransactionsParams;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.Transaction.Transaction;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, UUID>, JpaSpecificationExecutor<Transaction> {
    List<Transaction> findAllByUserId(UUID userId);

    @EntityGraph(attributePaths = { "category", "bankAccount", "person" }) // Create a joined fetch query
    Page<Transaction> findAllByUserId(UUID userId, Pageable pageable);

    Optional<Transaction> findByIdAndUserId(UUID transactionId, UUID userId);

    @Query("""
    select t from Transaction t
        where t.user.id = :userId
            and (lower(t.description) like lower(concat('%', :q, '%'))
                or (cast(t.amount as string) like lower(concat('%', :q, '%'))))
    """)
    @EntityGraph(attributePaths = { "category", "bankAccount", "person" })
    List<Transaction> search(@Param("userId") UUID userId, @Param("q") String q);
}
