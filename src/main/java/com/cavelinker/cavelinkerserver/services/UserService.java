package com.cavelinker.cavelinkerserver.services;

import com.cavelinker.cavelinkerserver.entities.User;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Stateless
public class UserService {

    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;
    public UserService() {}

    /*
    public long authenticateUser(String userName, String userPassword) {
        //write JPQL query that finds user via userName, checks that userPassword equals the password in the DB and returns userID
    } */
    public User addUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
        return user;
    }
    public User updateUser(User inputUser) {
        User userToBeUpdated=entityManager.find(User.class, inputUser.getUser_Id());
        entityManager.detach(userToBeUpdated);
        userToBeUpdated.setEmail(inputUser.getEmail());
        userToBeUpdated.setContactType(inputUser.getContactType());
        userToBeUpdated.setContactUserName(inputUser.getContactUserName());
        userToBeUpdated.setActivities(inputUser.getActivities());
        return entityManager.merge(userToBeUpdated);
    }
    public void deleteUser(long user_Id) {
        User user=entityManager.find(User.class, user_Id);
        entityManager.remove(user);
    }

    public User findUser(long user_Id) {
        return entityManager.find(User.class, user_Id);
    }
}
