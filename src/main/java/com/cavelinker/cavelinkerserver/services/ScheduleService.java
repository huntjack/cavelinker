package com.cavelinker.cavelinkerserver.services;

import com.cavelinker.cavelinkerserver.entities.Schedule;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ScheduleService {
    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;
    public ScheduleService() {}
    public Schedule addSchedule(Schedule schedule) {
        entityManager.persist(schedule);
        entityManager.flush();
        return schedule;
    }
    public Schedule updateSchedule(Schedule inputSchedule) {
        Schedule scheduleToBeUpdated=entityManager.find(Schedule.class, inputSchedule.getSchedule_Id());
        entityManager.detach(scheduleToBeUpdated);
        scheduleToBeUpdated.setStartTimeUtc(inputSchedule.getStartTimeUtc());
        scheduleToBeUpdated.setEndTimeUtc(inputSchedule.getEndTimeUtc());
        scheduleToBeUpdated.setUserTimeZone(inputSchedule.getUserTimeZone());
        return entityManager.merge(scheduleToBeUpdated);
    }
    public void deleteSchedule(long scheduleId) {
        Schedule schedule=entityManager.find(Schedule.class, scheduleId);
        entityManager.remove(schedule);
    }
}
