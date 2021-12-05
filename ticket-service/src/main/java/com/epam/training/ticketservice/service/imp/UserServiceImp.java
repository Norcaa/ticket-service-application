package com.epam.training.ticketservice.service.imp;

import com.epam.training.ticketservice.service.UserService;

public class UserServiceImp implements UserService {
    private Boolean loggedIn = false;

    @Override
    public void adminLogIn() {
        loggedIn = true;
    }

    @Override
    public void adminLogOut() {
        loggedIn = false;
    }

    @Override
    public boolean adminLoggedIn() {
        return loggedIn;
    }
}
