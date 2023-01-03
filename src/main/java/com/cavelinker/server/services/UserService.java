package com.cavelinker.server.services;

import com.cavelinker.server.entities.User;

public interface UserService {
    public User createUser(User user);
    public User getUser(long userId);
    public User updateUser(User inputUser);
    public void deleteUser(long userId);
}
