package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.entities.User;
import com.cavelinker.cavelinkerserver.services.ActivityService;
import com.cavelinker.cavelinkerserver.services.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("/secured/users/{userId}/activities")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {
    @Inject
    private ActivityService activityService;

    @Inject
    private UserService userService;

    @POST
    public Response createActivity(Activity activity, @PathParam("userId") long userId, @Context UriInfo uriInfo) {
        activity = activityService.createActivity(activity, userId);
        String newId = String.valueOf(activity.getActivityId());
        URI uri=uriInfo.getAbsolutePathBuilder()
                .path(newId)
                .build();
        return Response.created(uri)
                .entity(activity)
                .type("application/json")
                .build();
    }

    @GET
    @Path("/{activityId}")
    public Response getActivity(@PathParam("activityId") long activityId) {
        Activity activity=activityService.getActivity(activityId);
        return Response.ok()
                .entity(activity)
                .type("application/json")
                .build();
    }
    @PUT
    @Path("/{activityId}")
    public Response updateActivity(Activity activity, @PathParam("activityId") long activityId, @PathParam("userId") long userId) {
        activity.setActivityId(activityId);
        Activity updatedActivity=activityService.updateActivity(activity, userId);
        return Response.ok()
                .entity(updatedActivity)
                .type("application/json")
                .build();
    }
    @DELETE
    @Path("/{activityId}")
    public Response deleteActivity(@PathParam("userId")long userId, @PathParam("activityId") long activityId) {
        activityService.deleteActivity(userId, activityId);
        return Response.noContent()
                .build();
    }

}
