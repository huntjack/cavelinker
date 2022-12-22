package com.cavelinker.server.resources;

import com.cavelinker.server.entities.User;
import com.cavelinker.server.services.UserService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("/")
@RequestScoped
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
                .type("application/json")
                .build();
    }
    @GET
    @Path("users/{userId}")
    public Response getUser(@PathParam("userId") long userId) {
        User user=userService.getUser(userId);
        return Response.ok()
                .entity(user)
                .type("application/json")
                .build();
    }
    @PUT
    @Path("users/{userId}")
    public Response updateUser(User user, @PathParam("userId") long userId) {
        user.setUserId(userId);
        User updatedUser=userService.updateUser(user);
        return Response.ok()
                .entity(updatedUser)
                .type("application/json")
                .build();
    }
    @DELETE
    @Path("users/{userId}")
    public Response deleteUser(@PathParam("userId") long userId) {
        userService.deleteUser(userId);
        return Response.noContent()
                .build();
    }

}
