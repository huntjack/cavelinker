package com.cavelinker.cavelinkerserver.dataaccess;

import com.cavelinker.cavelinkerserver.model.User;

public interface DataAccessInterface <T> {
    public User save(T t);
}
