package com.cavelinker.cavelinkerserver.services;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.entities.Schedule;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ApplicationScoped
public class ScheduleService {
    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;
    @Transactional(rollbackOn={Exception.class})
    public Schedule createSchedule(Schedule schedule, long activityId) {
        schedule.convertToUtc();
        Activity activity = getActivityWithSchedules(activityId);
        entityManager.detach(activity);
        activity.addSchedule(schedule);
        activity = entityManager.merge(activity);
        entityManager.flush();
        int scheduleIndex = activity.getSchedules().indexOf(schedule);
        return activity.getSchedules().get(scheduleIndex);
    }
    @Transactional(rollbackOn={Exception.class})
    public Activity getActivityWithSchedules(long activityId) {
        TypedQuery<Activity> typedQuery = entityManager.createNamedQuery("getActivityWithSchedules", Activity.class)
                .setParameter("activityId", activityId);

        return typedQuery.getSingleResult();
    }
    @Transactional
    public Schedule getSchedule(long scheduleId) {
        return entityManager.find(Schedule.class, scheduleId);
    }
    @Transactional(rollbackOn={Exception.class})
    public Schedule updateSchedule(Schedule inputSchedule, long activityId) {
        inputSchedule.convertToUtc();
        Activity activity = getActivityWithSchedules(activityId);
        entityManager.detach(activity);
        int scheduleIndex = activity.getSchedules().indexOf(inputSchedule);
        activity.getSchedules()
                .get(scheduleIndex)
                .setStartTimeUtc(inputSchedule.getStartTimeUtc());
        activity.getSchedules()
                .get(scheduleIndex)
                .setEndTimeUtc(inputSchedule.getEndTimeUtc());
        activity.getSchedules()
                .get(scheduleIndex)
                .setUserTimeZone(inputSchedule.getUserTimeZone());
        entityManager.merge(activity);
        entityManager.flush();
        return activity
                .getSchedules()
                .get(scheduleIndex);
    }
    @Transactional(rollbackOn={Exception.class})
    public void deleteSchedule(long scheduleId, long activityId) {
        Activity activity = getActivityWithSchedules(activityId);
        Schedule schedule = entityManager.find(Schedule.class, scheduleId);
        int scheduleIndex = activity.getSchedules().indexOf(schedule);
        activity.removeSchedule(
                activity.getSchedules()
                        .get(scheduleIndex));
        entityManager.flush();
    }
    public ScheduleService() {}
}
