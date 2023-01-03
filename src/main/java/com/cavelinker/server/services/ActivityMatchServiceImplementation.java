package com.cavelinker.server.services;

import com.cavelinker.server.entities.Activity;
import com.cavelinker.server.entities.Schedule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class ActivityMatchServiceImplementation implements ActivityMatchService {
    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;
    @Transactional
    public Set<Activity> findMatchingActivities(long activityId) {
        TypedQuery<Activity> activityTypedQuery = entityManager.createNamedQuery("getActivityWithSchedules", Activity.class)
                .setParameter("activityId", activityId);
        Activity activity = activityTypedQuery.getSingleResult();
        Set<Activity> activities = new HashSet<>();
        for (Schedule schedule : activity.getSchedules()) {
            TypedQuery<Activity> matchingActiitiesTypedQuery = entityManager.createNamedQuery("findMatchingActivities", Activity.class)
                    .setParameter("activityType", activity.getActivityType())
                    .setParameter("serverName", activity.getServerName())
                    .setParameter("oneHourLessThanEndTime", schedule.getEndTimeUtc().minusHours(1))
                    .setParameter("oneHourMoreThanStartTime", schedule.getStartTimeUtc().plusHours(1));
            activities.addAll(matchingActiitiesTypedQuery.getResultList());
        }
        activities.remove(activity);
        return activities;
    }
    ActivityMatchServiceImplementation() {}
}
