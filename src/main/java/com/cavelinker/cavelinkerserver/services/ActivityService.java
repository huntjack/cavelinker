package com.cavelinker.cavelinkerserver.services;

import com.cavelinker.cavelinkerserver.entities.Activity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

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
        Activity activityToBeUpdated=entityManager.find(Activity.class, inputActivity.getActivityId());
        entityManager.detach(activityToBeUpdated);
        activityToBeUpdated.setUser(inputActivity.getUser());
        return entityManager.merge(activityToBeUpdated);
    }
    @Transactional
    public Activity updateActivity(Activity inputActivity) {
        Activity activityToBeUpdated=entityManager.find(Activity.class, inputActivity.getActivityId());
        entityManager.detach(activityToBeUpdated);
        activityToBeUpdated.setGamerTag(inputActivity.getGamerTag());
        activityToBeUpdated.setActivityType(inputActivity.getActivityType());
        activityToBeUpdated.setServerName(inputActivity.getServerName());
        activityToBeUpdated.setActivityMessage(inputActivity.getActivityMessage());
        return entityManager.merge(activityToBeUpdated);
    }
    public ActivityService() {}
}
