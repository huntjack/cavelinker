package com.cavelinker.server.services;

import com.cavelinker.server.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class UserService {

    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;

    @Transactional(rollbackOn={Exception.class})
    public User createUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }
    @Transactional
    public User getUser(long userId) {
        return entityManager.find(User.class, userId);
    }
    @Transactional(rollbackOn={Exception.class})
    public User updateUser(User inputUser) {
        User userToBeUpdated=entityManager.find(User.class, inputUser.getUserId());
        userToBeUpdated.setContactType(inputUser.getContactType());
        userToBeUpdated.setContactUserName(inputUser.getContactUserName());
        entityManager.flush();
        return userToBeUpdated;
    }
    @Transactional(rollbackOn={Exception.class})
    public void deleteUser(long userId) {
        User user=entityManager.find(User.class, userId);
        entityManager.remove(user);
        entityManager.flush();
    }

    public UserService() {}
}
