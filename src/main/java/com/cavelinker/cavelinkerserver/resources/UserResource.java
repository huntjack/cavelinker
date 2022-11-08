package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.model.User;
import com.cavelinker.cavelinkerserver.services.UserService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject private UserService userService;

    @POST
    public User addUser(User user) {
        return userService.addUser(user);
    }
    @DELETE
    @Path("/{userID}")
    public void deleteUser(@PathParam("userID") long userID) {
        userService.deleteUser(userID);
    }
    @PUT
    @Path("/{userID}")
    public User updateUser(@PathParam("userID") long userID, User user) {
        user.setUser_ID(userID);
        return userService.updateUser(user);
    }

}
