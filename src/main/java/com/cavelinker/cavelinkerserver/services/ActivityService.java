package com.cavelinker.cavelinkerserver.services;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.entities.Schedule;
import com.cavelinker.cavelinkerserver.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped
public class ActivityService {
    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;
    @Transactional(rollbackOn={Exception.class})
    public Activity createActivity(Activity activity, long userId) {
        User user = getUserWithActivities(userId);
        entityManager.detach(user);
        user.addActivity(activity);
        user = entityManager.merge(user);
        entityManager.flush();
        int activityIndex = user.getActivities().indexOf(activity);
        return user.getActivities().get(activityIndex);
    }

    @Transactional(rollbackOn={Exception.class})
    public User getUserWithActivities(long userId) {
        TypedQuery<User> typedQuery = entityManager.createNamedQuery("getUserWithActivities", User.class)
                .setParameter("userId", userId);

        return typedQuery.getSingleResult();
    }
   @Transactional
   public Activity getActivity(long activityId) {
        return entityManager.find(Activity.class, activityId);
   }
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
        return activities;
    }
    @Transactional(rollbackOn={Exception.class})
    public Activity updateActivity(Activity inputActivity, long userId) {
        User user = getUserWithActivities(userId);
        entityManager.detach(user);
        int activityIndex = user.getActivities().indexOf(inputActivity);
        user.getActivities()
                .get(activityIndex)
                .setGamerTag(inputActivity.getGamerTag());
        user.getActivities()
                .get(activityIndex)
                .setActivityType(inputActivity.getActivityType());
        user.getActivities()
                .get(activityIndex)
                .setServerName(inputActivity.getServerName());
        user.getActivities()
                .get(activityIndex)
                .setActivityMessage(inputActivity.getActivityMessage());
        entityManager.merge(user);
        entityManager.flush();
        return user
                .getActivities()
                .get(activityIndex);
    }
    @Transactional(rollbackOn={Exception.class})
    public void deleteActivity(long userId, long activityId) {
        User user = getUserWithActivities(userId);
        Activity activity = entityManager.find(Activity.class, activityId);
        int activityIndex = user.getActivities().indexOf(activity);
        user.removeActivity(
                user.getActivities()
                        .get(activityIndex));
        entityManager.flush();
    }
    public ActivityService() {}
}
