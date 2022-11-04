package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.model.User;
import com.cavelinker.cavelinkerserver.services.UserService;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
public class UserResource {

    UserService userService = new UserService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(User user) {
        return userService.addUser(user);
    }
}
