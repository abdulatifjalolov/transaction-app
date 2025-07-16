package com.example.testtask.repository.spec;

import com.example.testtask.model.Transaction;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class TransactionSpecification {

    public static Specification<Transaction> filter(Long userId, LocalDateTime from, LocalDateTime to) {
        return (Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            Predicate predicate = cb.conjunction();

            if (userId != null) {
                predicate = cb.and(predicate, cb.equal(root.get("user").get("id"), userId));
            }

            if (from != null && to != null) {
                predicate = cb.and(predicate, cb.between(root.get("createdAt"), from, to));
            }

            return predicate;
        };
    }
}
