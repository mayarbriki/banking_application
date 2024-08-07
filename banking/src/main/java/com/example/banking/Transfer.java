package com.example.banking;


import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "Transfer")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Long fromAccountId;

    @Column(nullable = false)
    private Long toAccountId;

    // Constructors, getters, and setters
    public Transfer() {
    }

    public Transfer(BigDecimal amount, LocalDateTime timestamp, Long fromAccountId, Long toAccountId) {
        this.amount = amount;
        this.timestamp = timestamp;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
    }

    // getters and setters omitted for brevity
}
