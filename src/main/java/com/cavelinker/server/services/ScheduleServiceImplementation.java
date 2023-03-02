package com.cavelinker.server.services;

import com.cavelinker.server.entities.Activity;
import com.cavelinker.server.entities.Schedule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ScheduleServiceImplementation implements ScheduleService {
    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;
    @Override
    @Transactional(rollbackOn={Exception.class})
    public Schedule createSchedule(Schedule schedule, long activityId) {
        schedule.convertToUtc();
        Activity activity = getActivityWithSchedules(activityId);
        entityManager.detach(activity);
        activity.addSchedule(schedule);
        activity = entityManager.merge(activity);
        entityManager.flush();
        int scheduleIndex = activity
                .getSchedules()
                .indexOf(schedule);
        return activity
                .getSchedules()
                .get(scheduleIndex);
    }
    @Override
    @Transactional(rollbackOn={Exception.class})
    public Activity getActivityWithSchedules(long activityId) {
        TypedQuery<Activity> typedQuery = entityManager.createNamedQuery("getActivityWithSchedules", Activity.class)
                .setParameter("activityId", activityId);

        return typedQuery.getSingleResult();
    }
    @Override
    @Transactional
    public Schedule getSchedule(long scheduleId) {
        return entityManager.find(Schedule.class, scheduleId);
    }
    @Override
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
    @Override
    @Transactional(rollbackOn={Exception.class})
    public void deleteSchedule(long scheduleId, long activityId) {
        Activity activity = getActivityWithSchedules(activityId);
        Schedule schedule = entityManager.find(Schedule.class, scheduleId);
        int scheduleIndex = activity
                .getSchedules()
                .indexOf(schedule);
        activity.removeSchedule(
                activity.getSchedules()
                        .get(scheduleIndex));
        entityManager.flush();
    }
}
