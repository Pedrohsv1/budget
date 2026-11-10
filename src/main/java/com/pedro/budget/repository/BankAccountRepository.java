package com.pedro.budget.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.pedro.budget.entity.BankAccount;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BankAccountRepository extends JpaRepository<BankAccount, UUID> {
    Optional<BankAccount> findByIdAndUserId(UUID bankId, UUID userId);

    Page<BankAccount> findAllByUserId(UUID userId, Pageable pageable);

    @Query("""
        select b from BankAccount b
            where b.user.id = :userId
                and (lower(b.description) like lower(concat('%', :q, '%'))
                    or (lower(b.type) like lower(concat('%', :q, '%'))))
    """)
    List<BankAccount> search(@Param("userId") UUID userId, @Param("q") String q);
}
