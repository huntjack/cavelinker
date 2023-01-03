package com.cavelinker.server.resources;

import com.cavelinker.server.entities.Activity;
import com.cavelinker.server.services.ActivityMatchService;
import com.cavelinker.server.services.ActivityService;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.net.URI;
import java.util.Set;

@Path("/users/{userId}/activities")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {
    @Inject
    private ActivityService activityService;
    @Inject
    private ActivityMatchService activityMatchService;

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
        Set<Activity> activities = activityMatchService.findMatchingActivities(activityId);
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
