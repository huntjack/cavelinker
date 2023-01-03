package com.cavelinker.server.services;

import com.cavelinker.server.entities.Activity;
import com.cavelinker.server.entities.Schedule;
import jakarta.transaction.Transactional;

public interface ScheduleService {
    @Transactional(rollbackOn = {Exception.class})
    Schedule createSchedule(Schedule schedule, long activityId);

    @Transactional(rollbackOn = {Exception.class})
    Activity getActivityWithSchedules(long activityId);

    @Transactional
    Schedule getSchedule(long scheduleId);

    @Transactional(rollbackOn = {Exception.class})
    Schedule updateSchedule(Schedule inputSchedule, long activityId);

    @Transactional(rollbackOn = {Exception.class})
    void deleteSchedule(long scheduleId, long activityId);
}
