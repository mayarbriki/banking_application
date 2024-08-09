package com.example.banking;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class WithdrawalService {

    private final UserRepository userRepository;
    private final WithdrawalRepository withdrawalRepository;

    public WithdrawalService(UserRepository userRepository, WithdrawalRepository withdrawalRepository) {
        this.userRepository = userRepository;
        this.withdrawalRepository = withdrawalRepository;
    }

    @Transactional
    public String withdraw(Long userId, BigDecimal amount) {
        Optional<User> userOpt = userRepository.findById(userId);

        if (userOpt.isEmpty()) {
            return "User not found";
        }

        User user = userOpt.get();

        // Check if the user has sufficient balance
        if (user.getAccount_balance().compareTo(amount) >= 0) {
            // Subtract the amount from user's balance
            user.setAccount_balance(user.getAccount_balance().subtract(amount));
            userRepository.save(user);

            // Create and save the withdrawal record
            Withdrawal withdrawal = new Withdrawal(amount, LocalDateTime.now(), userId);
            withdrawalRepository.save(withdrawal);

            return "Withdrawal successful";
        } else {
            return "Insufficient funds";
        }
    }
}
