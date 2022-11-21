package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.cavelinker.cavelinkerserver.model.User;
import com.cavelinker.cavelinkerserver.services.UserService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("/users")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject private UserService userService;

    @GET
    @Path("/test")
    public Response isServerUp() {
        User user=new User("hunt.jack01@gmail.com", "myPassword", ContactType.FACEBOOK, "Eric Blackwood");
        return Response.ok()
                .entity(user)
                .build();
    }

    @POST
    public Response addUser(User user, @Context UriInfo uriInfo) {
        User persistedUser=userService.addUser(user);
        String newID=String.valueOf(persistedUser.getUser_ID());
        URI uri=uriInfo.getAbsolutePathBuilder()
                .path(newID)
                .build();
        return Response.created(uri)
                .entity(persistedUser)
                .build();
    }
    @DELETE
    @Path("/{userID}")
    public Response deleteUser(@PathParam("userID") long userID) {
        userService.deleteUser(userID);
        return Response.noContent()
                .build();
    }
    @PUT
    @Path("/{userID}")
    public Response updateUser(@PathParam("userID") long userID, User user) {
        user.setUser_ID(userID);
        User updatedUser=userService.updateUser(user);
        return Response.ok()
                .entity(updatedUser)
                .build();
    }

}
