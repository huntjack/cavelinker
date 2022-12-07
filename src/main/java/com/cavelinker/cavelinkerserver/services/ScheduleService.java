package com.cavelinker.cavelinkerserver.services;

import com.cavelinker.cavelinkerserver.entities.Schedule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ScheduleService {
    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;
    @Transactional
    public Schedule createSchedule(Schedule schedule) {
        entityManager.persist(schedule);
        entityManager.flush();
        return schedule;
    }
    @Transactional
    public Schedule updateSchedule(Schedule inputSchedule) {
        Schedule scheduleToBeUpdated=entityManager.find(Schedule.class, inputSchedule.getScheduleId());
        entityManager.detach(scheduleToBeUpdated);
        scheduleToBeUpdated.setStartTimeUtc(inputSchedule.getStartTimeUtc());
        scheduleToBeUpdated.setEndTimeUtc(inputSchedule.getEndTimeUtc());
        scheduleToBeUpdated.setUserTimeZone(inputSchedule.getUserTimeZone());
        return entityManager.merge(scheduleToBeUpdated);
    }
    @Transactional
    public void deleteSchedule(long scheduleId) {
        Schedule schedule=entityManager.find(Schedule.class, scheduleId);
        entityManager.remove(schedule);
    }
    public ScheduleService() {}
}
