package com.example.banking;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Withdrawal")
public class Withdrawal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Long accountId;

    // Constructors, getters, and setters
    public Withdrawal() {
    }

    public Withdrawal(BigDecimal amount, LocalDateTime timestamp, Long accountId) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.accountId = accountId;
    }

    // getters and setters omitted for brevity
}
