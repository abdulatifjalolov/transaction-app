package com.example.testtask.model;

import com.example.testtask.model.enums.BalanceType;
import com.example.testtask.utils.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "balances", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "type"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Balance extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BalanceType type;

    @Column(nullable = false)
    private Double amount = 0.0;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
}
