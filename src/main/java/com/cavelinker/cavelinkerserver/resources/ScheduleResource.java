package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.entities.Schedule;
import com.cavelinker.cavelinkerserver.services.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;

@Path("/secured/users/{userId}/activities/{activityId}/schedules")
@RequestScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ScheduleResource {
    @Inject
    private ScheduleService scheduleService;

    private static ObjectMapper mapper;

    static {
        mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }

    @POST
    public Response createSchedule(Schedule schedule, @PathParam("activityId") long activityId, @Context UriInfo uriInfo) {
        schedule = scheduleService.createSchedule(schedule, activityId);
        String newId = String.valueOf(schedule.getScheduleId());
        URI uri = uriInfo.getAbsolutePathBuilder()
                .path(newId)
                .build();
        return Response.created(uri)
                .entity(schedule)
                .type("application/json")
                .build();
    }
    @GET
    @Path("/{scheduleId}")
    public Response getSchedule(@PathParam("scheduleId") long scheduleId) {
        Schedule schedule = scheduleService.getSchedule(scheduleId);
        return Response.ok()
                .entity(schedule)
                .type("application/json")
                .build();
    }
    @PUT
    @Path("/{scheduleId}")
    public Response updateSchedule(Schedule schedule, @PathParam("scheduleId") long scheduleId, @PathParam("activityId") long activityId) {
        schedule.setScheduleId(scheduleId);
        Schedule updatedSchedule=scheduleService.updateSchedule(schedule, activityId);
        return Response.ok()
                .entity(updatedSchedule)
                .type("application/json")
                .build();
    }
    @DELETE
    @Path("/{scheduleId}")
    public Response deleteSchedule(@PathParam("scheduleId") long scheduleId, @PathParam("activityId") long activityId) {
        scheduleService.deleteSchedule(scheduleId, activityId);
        return Response.noContent()
                .build();
    }
}
