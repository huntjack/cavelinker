package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.model.User;
import com.cavelinker.cavelinkerserver.services.UserService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
@Stateless
public class UserResource {

    @Inject private UserService userService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public User addUser(User user) {
        return userService.addUser(user);
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String test() {return "test";}

}
