package com.example.springboot_check;

import java.util.Map;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Returns logged-in GitHub user info as JSON
    @GetMapping("/api/userinfo")
    public Map<String, Object> getUserInfo(OAuth2AuthenticationToken authentication) {
        Map<String, Object> userAttributes = authentication.getPrincipal().getAttributes();

        return Map.of(
            "username", userAttributes.get("login"),
            "avatarUrl", userAttributes.get("avatar_url"),
            "email", userAttributes.get("email")
        );
    }

    // Shows dashboard page & saves user to DB if new
    @GetMapping("/dashboard")
    public String dashboard(OAuth2AuthenticationToken authentication, Model model) {
        Map<String, Object> userAttributes = authentication.getPrincipal().getAttributes();

        String username = (String) userAttributes.get("login");
        String avatarUrl = (String) userAttributes.get("avatar_url");
        String githubId = String.valueOf(userAttributes.get("id"));
        String email = (String) userAttributes.get("email"); // may be null if private

        // Send values to dashboard.html
        model.addAttribute("username", username);
        model.addAttribute("avatar", avatarUrl);

        // Delegate user creation/retrieval to service layer
        userService.findOrCreateUser(githubId, username, email, avatarUrl);

        return "dashboard"; // render dashboard.html
    }

    // Profile page showing logged-in user's avatar + name
    @GetMapping("/profile")
    public String profilePage(OAuth2AuthenticationToken authentication, Model model) {
        String username = (String) authentication.getPrincipal().getAttributes().get("login");
        String avatarUrl = (String) authentication.getPrincipal().getAttributes().get("avatar_url");

        model.addAttribute("username", username);
        model.addAttribute("avatar", avatarUrl);

        return "profile"; // render profile.html
    }

    // Chat page for logged-in users
    @GetMapping("/chat")
    public String chatPage(OAuth2AuthenticationToken authentication, Model model) {
        String username = (String) authentication.getPrincipal().getAttributes().get("login");
        String avatarUrl = (String) authentication.getPrincipal().getAttributes().get("avatar_url");

        model.addAttribute("username", username);
        model.addAttribute("avatar", avatarUrl);

        return "chat"; // render chat.html
    }

    // Home page mapping
    @GetMapping("/")
    public String home() {
        return "home";
    }

    // Logout user and show simple text message
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, null);
        return "You are logged out. <a href='/dashboard'>Login again</a>";
    }
}
