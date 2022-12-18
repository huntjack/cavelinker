package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.services.ActivityService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.List;
import java.util.Set;

@Path("/secured/users/{userId}/activities")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {
    @Inject
    private ActivityService activityService;

    @POST
    public Response createActivity(Activity activity, @PathParam("userId") long userId, @Context UriInfo uriInfo) {
        activity = activityService.createActivity(activity, userId);
        String newId = String.valueOf(activity.getActivityId());
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(newId)
                .build();
        return Response.created(uri)
                .entity(activity)
                .type("application/json")
                .build();
    }

    @GET
    @Path("/{activityId}")
    public Response findMatchingActivities(@PathParam("activityId") long activityId) {
        Set<Activity> activities = activityService.findMatchingActivities(activityId);
        GenericEntity<Set<Activity>> entities = new GenericEntity<>(activities){};
        return Response.ok()
                .entity(entities)
                .type("application/json")
                .build();
    }
    @PUT
    @Path("/{activityId}")
    public Response updateActivity(Activity activity, @PathParam("activityId") long activityId, @PathParam("userId") long userId) {
        activity.setActivityId(activityId);
        Activity updatedActivity = activityService.updateActivity(activity, userId);
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
