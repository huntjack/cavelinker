package com.cavelinker.cavelinkerserver.services;

import com.cavelinker.cavelinkerserver.model.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Stateless
public class UserService {

    @PersistenceContext(unitName = "Cavelinker_Database")
    EntityManager entityManager;
    public UserService() {}
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }
    public void deleteUser(long userID) {
        User user=entityManager.find(User.class, userID);
        entityManager.remove(user);
    }
    public User updateUser(User inputUser) {
        User userToBeUpdated=entityManager.find(User.class, inputUser.getUser_ID());
        entityManager.detach(userToBeUpdated);
        userToBeUpdated.setEmail(inputUser.getEmail());
        userToBeUpdated.setPassword(inputUser.getPassword());
        userToBeUpdated.setContactType(inputUser.getContactType());
        userToBeUpdated.setContactUserName(inputUser.getContactUserName());
        return entityManager.merge(userToBeUpdated);
    }
}
