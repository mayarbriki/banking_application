package com.example.banking;


import com.example.banking.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // You can define custom query methods here if needed
Optional getUserById (Long id);
    Optional<User> findById(Long id);
    Optional<User> findByUsernameAndPassword(String username, String password);
}
