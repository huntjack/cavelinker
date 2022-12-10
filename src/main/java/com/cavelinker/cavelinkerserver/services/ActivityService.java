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
    public Activity createActivity(Activity activity) {
        entityManager.persist(activity);
        entityManager.flush();
        return activity;
    }
    @Transactional
    public Activity saveUserMapping(Activity inputActivity) {
        Activity activityToBeUpdated = entityManager.find(Activity.class, inputActivity.getActivityId());
        entityManager.detach(activityToBeUpdated);
        activityToBeUpdated.setUser(inputActivity.getUser());
        return entityManager.merge(activityToBeUpdated);
    }
    @Transactional
    public Activity updateActivity(Activity inputActivity) {
        Activity activityToBeUpdated = entityManager.find(Activity.class, inputActivity.getActivityId());
        User user = entityManager.find(User.class, activityToBeUpdated.getUser().getUserId());
        entityManager.detach(user);
        List<Activity> activities=user.getActivities();
        int activityIndex=activities.indexOf(inputActivity);
        if(activities.contains(inputActivity)) {
            activities.set(activityIndex, inputActivity);
        }
        user.setActivities(activities);
        entityManager.merge(user);
        return activities.get(activityIndex);
    }
    @Transactional
    public void deleteActivity(long userId, long activityId) {
        User userToBeUpdated = entityManager.find(User.class, userId);
        Activity activityToBeRemoved = entityManager.find(Activity.class, activityId);
        entityManager.detach(userToBeUpdated);
        userToBeUpdated.removeActivity(activityToBeRemoved);
        entityManager.merge(userToBeUpdated);
    }
    public ActivityService() {}
}
