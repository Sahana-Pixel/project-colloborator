package com.example.springboot_check;

import org.springframework.stereotype.Service;

/**
 * Service layer for user-related business logic.
 * Handles user creation, retrieval, and management operations.
 */
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds or creates a user based on GitHub OAuth information.
     * If user doesn't exist, creates a new user entity and saves it.
     * 
     * Business Logic Moved from UserController.dashboard():
     * - Checks if user exists by GitHub ID
     * - Creates new user if not found
     * - Returns existing or newly created user
     * 
     * @param githubId GitHub user ID
     * @param username GitHub username
     * @param email GitHub email (may be null)
     * @param avatarUrl GitHub avatar URL
     * @return UserEntity (existing or newly created)
     */
    public UserEntity findOrCreateUser(String githubId, String username, String email, String avatarUrl) {
        // Check if user already exists in DB
        UserEntity user = userRepository.findByGithubId(githubId);

        if (user == null) {
            // Create and save new user to database
            user = new UserEntity();
            user.setGithubId(githubId);
            user.setUsername(username);
            user.setEmail(email);
            user.setAvatarUrl(avatarUrl);
            user = userRepository.save(user);
        }

        return user;
    }

    /**
     * Finds a user by GitHub ID.
     * 
     * @param githubId GitHub user ID
     * @return UserEntity or null if not found
     */
    public UserEntity findByGithubId(String githubId) {
        return userRepository.findByGithubId(githubId);
    }
}
