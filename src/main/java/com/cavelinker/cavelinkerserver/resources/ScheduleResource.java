package com.cavelinker.cavelinkerserver.resources;

import com.cavelinker.cavelinkerserver.entities.Schedule;
import com.cavelinker.cavelinkerserver.services.ScheduleService;
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
public class ScheduleResource {
    @Inject
    private ScheduleService scheduleService;

    @POST
    public Response addSchedule(Schedule schedule, @Context UriInfo uriInfo) {
        schedule=scheduleService.addSchedule(schedule);
        String newId=String.valueOf(schedule.getSchedule_Id());
        URI uri=uriInfo.getAbsolutePathBuilder()
                .path(newId)
                .build();
        return Response.created(uri)
                .entity(schedule)
                .build();
    }
    @PUT
    @Path("{scheduleId}")
    public Response updateSchedule(@PathParam("scheduleId") long scheduleId, Schedule schedule) {
        schedule.setSchedule_Id(scheduleId);
        Schedule updatedSchedule=scheduleService.updateSchedule(schedule);
        return Response.ok()
                .entity(updatedSchedule)
                .build();
    }
    @DELETE
    @Path("{scheduleId}")
    public Response deleteSchedule(@PathParam("scheduleId") long scheduleId) {
        scheduleService.deleteSchedule(scheduleId);
        return Response.noContent()
                .build();
    }
}
