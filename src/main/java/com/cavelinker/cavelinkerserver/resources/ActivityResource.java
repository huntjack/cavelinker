package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.services.ActivityService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("/")
@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {
    @Inject
    private ActivityService activityService;

    @POST
    public Response addActivity(Activity activity, @Context UriInfo uriInfo) {
        Activity persistedActivity=activityService.addActivity(activity);

         /*need to set user(from PathParam) on activity side and add activity to activities list on User side using User.AddActivity
        Prerequisite: UserService needs findUser(User_id userid) method Note:This should be used in other UserService methods too
        Step 1: Find user with PathParam using UserService
        Step 2: Call user.AddActivity(persistedActivity)
        Step 3: Merge(user)
        Step 4: Merge(persistedActivity)
         */

        String newId=String.valueOf(persistedActivity.getActivity_Id());
        URI uri=uriInfo.getAbsolutePathBuilder()
                .path(newId)
                .build();
        return Response.created(uri)
                .entity(persistedActivity)
                .build();
    }
    @PUT
    @Path("{activityId}")
    public Response updateUser(@PathParam("activityId") long activityId, Activity activity) {
        activity.setActivity_Id(activityId);
        Activity updatedActivity=activityService.updateActivity(activity);
        return Response.ok()
                .entity(updatedActivity)
                .build();
    }
    @DELETE
    @Path("{activityId}")
    public Response deleteActivity(@PathParam("activityId") long activityId) {
        activityService.deleteActivity(activityId);
        return Response.noContent()
                .build();
    }

    @Path("{activityId}/schedules")
    public ScheduleResource getScheduleResource() {
        return new ScheduleResource();
    }

}
