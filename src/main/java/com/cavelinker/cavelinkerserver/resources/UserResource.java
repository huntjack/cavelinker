package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.entities.User;
import com.cavelinker.cavelinkerserver.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("/")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    private UserService userService;

    @POST
    @Path("users")
    public Response createUser(User user, @Context UriInfo uriInfo) {
        user=userService.createUser(user);
        String newId=String.valueOf(user.getUserId());
        URI uri=uriInfo.getAbsolutePathBuilder()
                .path(newId)
                .build();
        return Response.created(uri)
                .entity(user)
                .build();
    }
    @PUT
    @Path("secured/users/{userId}")
    public Response updateUser(@PathParam("userId") long userId, User user) {
        user.setUserId(userId);
        User updatedUser=userService.updateUser(user);
        return Response.ok()
                .entity(updatedUser)
                .build();
    }
    @DELETE
    @Path("secured/users/{userId}")
    public Response deleteUser(@PathParam("userId") long userId) {
        userService.deleteUser(userId);
        return Response.noContent()
                .build();
    }

}
