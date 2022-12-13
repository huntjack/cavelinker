package com.cavelinker.cavelinkerserver.services;

import com.cavelinker.cavelinkerserver.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class UserService {

    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;

    /*
    public long authenticateUser(String userName, String userPassword) {
        //write JPQL query that finds user via userName, checks that userPassword equals the password in the DB and returns userID
    } */
    @Transactional
    public User createUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }
    @Transactional
    public User getUser(long userId) {
        return entityManager.find(User.class, userId);
    }
    @Transactional
    public User updateUser(User inputUser) {
        User userToBeUpdated=entityManager.find(User.class, inputUser.getUserId());
        userToBeUpdated.setEmail(inputUser.getEmail());
        userToBeUpdated.setContactType(inputUser.getContactType());
        userToBeUpdated.setContactUserName(inputUser.getContactUserName());
        entityManager.flush();
        return userToBeUpdated;
    }
    @Transactional
    public void deleteUser(long userId) {
        User user=entityManager.find(User.class, userId);
        entityManager.remove(user);
        entityManager.flush();
    }

    public UserService() {}
}
