package com.cavelinker.server.services;

import com.cavelinker.server.entities.Activity;

import java.util.Set;

public interface ActivityMatchService {
    public Set<Activity> findMatchingActivities(long activityId);
}
