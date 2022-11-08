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
}
