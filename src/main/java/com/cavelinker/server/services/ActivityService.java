package com.cavelinker.server.services;

import com.cavelinker.server.entities.Activity;
import com.cavelinker.server.entities.User;

public interface ActivityService {
    public Activity createActivity(Activity activity, long userId);
    public User getUserWithActivities(long userId);
    public Activity updateActivity(Activity inputActivity, long userId);
    public void deleteActivity(long userId, long activityId);
}
