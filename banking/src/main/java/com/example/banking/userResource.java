package com.example.banking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
@CrossOrigin(origins = "http://localhost:4200") // Change this to your Angular app's URL
@RestController
@RequestMapping("/user")
public class userResource {
    private final UserService userservice;
    private final TransferRepository transferRepository;
    private final WithdrawalService withdrawalService;
    public userResource(UserService userservice, WithdrawalService withdrawalService, TransferRepository transferRepository) {
        this.userservice = userservice;
        this.withdrawalService = withdrawalService;
        this.transferRepository = transferRepository;
    }
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@RequestBody User user) { // Method name should follow camelCase convention
        User newuser = userservice.addUser(user);
        return new ResponseEntity<>(newuser, HttpStatus.CREATED);
    }
    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userservice.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(@RequestParam Long userId, @RequestParam BigDecimal amount) {
        String result = withdrawalService.withdraw(userId, amount);
        if ("Withdrawal successful".equals(result)) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        User user = userservice.getUserById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
        userservice.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestBody Transfer transfer) {
        Long fromAccountId = transfer.getFromAccountId();
        Long toAccountId = transfer.getToAccountId();
        BigDecimal amount = transfer.getAmount();

        System.out.println("Received transfer request - fromAccountId: " + fromAccountId +
                ", toAccountId: " + toAccountId + ", amount: " + amount);

        ResponseEntity<String> response = userservice.transfer(fromAccountId, toAccountId, amount);

        System.out.println("Transfer response: " + response.getBody());

        return response;
    }





    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updateuser = userservice.updateUser(user);
        return new ResponseEntity<>(updateuser, HttpStatus.OK);
    }
    @PostMapping("/signup")
    public ResponseEntity<User> signUp(@RequestBody User user) {
        User newUser = userservice.signUp(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<User> signIn(@RequestBody User user) {
        User authenticatedUser = userservice.signIn(user);
        if (authenticatedUser != null) {
            return ResponseEntity.ok(authenticatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/balance/{id}")
    public ResponseEntity<Map<String, Object>> getAccountBalance(@PathVariable("id") Long id) {
        Optional<User> userOpt = userservice.findUserById(id);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            Map<String, Object> response = new HashMap<>();
            response.put("balance", user.getAccount_balance());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
