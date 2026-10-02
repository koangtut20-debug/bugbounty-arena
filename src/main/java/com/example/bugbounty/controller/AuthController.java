package com.example.bugbounty.controller;

import com.example.bugbounty.model.User;
import com.example.bugbounty.dto.LeaderboardUser;
import com.example.bugbounty.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public String register(@RequestBody User user) {

        if (userRepository.findByEmail(user.getEmail()) != null) {
            return "Email already registered";
        }

        userRepository.save(user);

        return "Registration successful";
    }

    @PostMapping("/points/add")
    public String addPoints(
            @RequestParam String email,
            @RequestParam int points) {

        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            return "User not found";
        }

        existingUser.setPoints(existingUser.getPoints() + points);

        userRepository.save(existingUser);

        return "Points added successfully";
    }

    @GetMapping("/user/points")
    public int getPoints(@RequestParam String email) {

        User existingUser = userRepository.findByEmail(email);

        if (existingUser == null) {
            return 0;
        }

        return existingUser.getPoints();
    }

    @GetMapping("/leaderboard")
    public List<LeaderboardUser> getLeaderboard() {

        return userRepository.findAllByOrderByPointsDesc()
                .stream()
                .map(user -> new LeaderboardUser(
                        user.getName(),
                        user.getPoints()
                ))
                .collect(Collectors.toList());
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser == null) {
            return "User not found";
        }

        if (existingUser.getPassword() == null) {
            return "Password not set for this account";
        }

        if (user.getPassword() == null) {
            return "Password required";
        }

        if (!existingUser.getPassword().equals(user.getPassword())) {
            return "Invalid password";
        }

        return "Login successful";
    }
}
