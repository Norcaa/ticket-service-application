package com.epam.training.ticketservice.presentation.cli.handler;

import com.epam.training.ticketservice.service.UserService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
public class AuthCommandHandler {

    private UserService userService;
    private String username;
    private String password;

    public AuthCommandHandler(UserService userService) {
        this.userService = userService;
    }

    @ShellMethod(value = "Sign in privileged", key = "sign in privileged")
    public String signInPrivileged(final String username, String password) {
        if (username.equals("admin") && password.equals("admin")) {
            userService.adminLogIn();
            this.username = username;
            this.password = password;
            return "Successful login.";
        }
        return "Login failed due to incorrect credentials";
    }

    @ShellMethod(value = "sign out", key = "sign out")
    public String signOut() {
        userService.adminLogOut();
        return "Successful log out.";
    }

    @ShellMethod(value = "Describe Account", key = "describe account")
    public String describeAccount() {
        if (userService.adminLoggedIn()) {
            return "Signed in with privileged account '" + this.username + "'";
        }
        return "You are not signed in";
    }
}
