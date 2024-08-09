package com.example.banking;

import com.example.banking.User;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;
    /*
    @Autowired
    private PasswordEncoder passwordEncoder; // For password hashing and comparison
    */
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
    }

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Example of using UserRepository methods
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User updateUser(User user) {
        return userRepository.save(user); // Assuming save will handle both create and update
    }
    public User addUser(User user) {
        user.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE); // Generate a positive random Long ID
        return userRepository.save(user);
    }
    public User signUp(User user) {
        // Add any additional sign-up logic here, such as password hashing
        return userRepository.save(user);
    }

    public User signIn(User user) {
        // Add your authentication logic here, such as checking the username and password
        Optional<User> foundUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        return foundUser.orElse(null);
    }
    /*
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Note: This method should not be used for direct username-password checks.
        // Use this for user details retrieval.
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Return UserDetails with authorities
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole()))
        );
    }
    public User authenticateUser(String username, String password) {
        // Find user by username and password
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);

        // Verify password and return user if valid
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user.get();
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

     */
    public BigDecimal getAccountBalance(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        return user.map(User::getAccountBalance).orElse(BigDecimal.valueOf(0.00));
    }
    public BigDecimal someMethod() {
        return BigDecimal.valueOf(0.00); // returns a BigDecimal representing 0.00
    }
    @Autowired
    private TransferRepository transferRepository;
    @Transactional
    public ResponseEntity<String> transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {
        Optional<User> fromUserOpt = userRepository.findById(fromAccountId);
        Optional<User> toUserOpt = userRepository.findById(toAccountId);

        if (fromUserOpt.isEmpty() || toUserOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        User fromUser = fromUserOpt.get();
        User toUser = toUserOpt.get();

        if (fromUser.getAccountBalance().compareTo(amount) >= 0) {
            fromUser.setAccountBalance(fromUser.getAccountBalance().subtract(amount));
            toUser.setAccountBalance(toUser.getAccountBalance().add(amount));
            userRepository.save(fromUser);
            userRepository.save(toUser);

            Transfer transfer = new Transfer(amount, LocalDateTime.now(), fromAccountId, toAccountId);
            transferRepository.save(transfer);

            return ResponseEntity.ok("Transfer successful");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient funds");
        }
    }



}
