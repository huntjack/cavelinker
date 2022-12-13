package com.cavelinker.cavelinkerserver.services;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.entities.User;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class ActivityService {
    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;
    @Transactional
    public Activity createActivity(Activity activity, long userId) {
        User user = (User) entityManager.createNamedQuery("getUserWithActivities")
                .setParameter("userId", userId)
                .getSingleResult();
        entityManager.detach(user);
        user.addActivity(activity);
        user = entityManager.merge(user);
        entityManager.flush();
        int activityIndex = user.getActivities().indexOf(activity);
        return user.getActivities().get(activityIndex);
    }
    @Transactional
    public Activity getActivity(long activityId) {
        return entityManager.find(Activity.class, activityId);
    }
    @Transactional
    public Activity updateActivity(Activity inputActivity, long userId) {
        User user = (User) entityManager.createNamedQuery("getUserWithActivities")
                .setParameter("userId", userId)
                .getSingleResult();
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
                .get(user.getActivities().indexOf(inputActivity))
                .setActivityMessage(inputActivity.getActivityMessage());
        entityManager.merge(user);
        entityManager.flush();
        return user
                .getActivities()
                .get(activityIndex);
    }
    @Transactional
    public void deleteActivity(long userId, long activityId) {
        User user = (User) entityManager.createNamedQuery("getUserWithActivities")
                .setParameter("userId", userId)
                .getSingleResult();
        Activity activity = entityManager.find(Activity.class, activityId);
        entityManager.detach(user);
        int activityIndex = user.getActivities().indexOf(activity);
        user.removeActivity(
                user.getActivities()
                        .get(activityIndex));
        entityManager.merge(user);
        entityManager.flush();
    }
    public ActivityService() {}
}
