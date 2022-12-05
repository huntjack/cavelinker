package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.entities.User;
import com.cavelinker.cavelinkerserver.services.ActivityService;
import com.cavelinker.cavelinkerserver.services.UserService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Stateless
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ActivityResource {
    @Inject
    private ActivityService activityService;

    @Inject
    private UserService userService;

    @POST
    public Response addActivity(Activity activity, @PathParam("userId") long userId, @Context UriInfo uriInfo) {
        activity=activityService.addActivity(activity);
         /*need to set user(from PathParam) on activity side and add activity to activities list on User side using User.AddActivity
        Prerequisite: UserService needs findUser(User_id userid) method Note:This should be used in other UserService methods too
        Step 1: Find user with PathParam using UserService
        Step 2: Call user.addActivity(activity) //added mapping to entities, but not merged yet
        Step 3: userService.updateUser(user); //change updateUser() to update mapping variables
        Step 4: activityService.updateActivity(activity); //change updateActivity to update mapping variables
         */
        User user=userService.findUser(userId);
        user.addActivity(activity);
        userService.updateUser(user);
        activityService.updateActivity(activity);
        String newId=String.valueOf(activity.getActivity_Id());
        URI uri=uriInfo.getAbsolutePathBuilder()
                .path(newId)
                .build();
        return Response.created(uri)
                .entity(activity)
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
