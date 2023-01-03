package com.cavelinker.server.services;

import com.cavelinker.server.entities.Activity;
import com.cavelinker.server.entities.Schedule;
import com.cavelinker.server.entities.User;
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
