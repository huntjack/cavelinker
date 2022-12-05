package com.cavelinker.cavelinkerserver.services;

import com.cavelinker.cavelinkerserver.entities.Activity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class ActivityService {
    @PersistenceContext(unitName = "cavelinker_database")
    EntityManager entityManager;
    public ActivityService() {}
    public Activity addActivity(Activity activity) {
        entityManager.persist(activity);
        entityManager.flush();
        return activity;
    }
    public Activity updateActivity(Activity inputActivity) {
        Activity activityToBeUpdated=entityManager.find(Activity.class, inputActivity.getActivity_Id());
        entityManager.detach(activityToBeUpdated);
        activityToBeUpdated.setGamerTag(inputActivity.getGamerTag());
        activityToBeUpdated.setActivityType(inputActivity.getActivityType());
        activityToBeUpdated.setServerName(inputActivity.getServerName());
        activityToBeUpdated.setActivityMessage(inputActivity.getActivityMessage());
        activityToBeUpdated.setUser(inputActivity.getUser());
        return entityManager.merge(activityToBeUpdated);
    }
    public void deleteActivity(long activityId) {
        Activity activity=entityManager.find(Activity.class, activityId);
        entityManager.remove(activity);
    }
}
