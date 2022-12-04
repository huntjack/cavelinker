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
    public User addUser(User user) {
        entityManager.persist(user);
        return user;
    }
    /*
    public long authenticateUser(String userName, String userPassword) {
        //write JPQL query that finds user via userName, checks that userPassword equals the password in the DB and returns userID
    } */
    public User updateUser(User inputUser) {
        User userToBeUpdated=entityManager.find(User.class, inputUser.getUser_Id());
        entityManager.detach(userToBeUpdated);
        userToBeUpdated.setEmail(inputUser.getEmail());
        userToBeUpdated.setContactType(inputUser.getContactType());
        userToBeUpdated.setContactUserName(inputUser.getContactUserName());
        return entityManager.merge(userToBeUpdated);
    }
    public void deleteUser(long userID) {
        User user=entityManager.find(User.class, userID);
        entityManager.remove(user);
    }
}
