package com.cavelinker.cavelinkerserver.systemtests;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.enums.ActivityType;
import com.cavelinker.cavelinkerserver.enums.ServerName;
import com.cavelinker.cavelinkerserver.testenvironmentsetup.CaveLinkerIT;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;

import java.util.UUID;

import static org.hamcrest.Matchers.*;

public class ActivityIT extends CaveLinkerIT {

    @Test
    public void postActivityHappyPath() {
        Activity postActivity;
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(postActivity = new Activity("postGamerTag", UUID.randomUUID().toString(), ActivityType.EXTREME_OLD, ServerName.CHAOS, "postActivityMessage"))
                .when()
                .post("/secured/users/3/activities")
                .then()
                .assertThat()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .body("gamerTag", equalTo(postActivity.getGamerTag()))
                .body("activityType", equalTo(postActivity.getActivityType().toString()))
                .body("serverName", equalTo(postActivity.getServerName().toString()))
                .body("activityMessage", equalTo(postActivity.getActivityMessage()));
    }
/*
    @Test
    public void updateActivityHappyPath() {
        Activity updateActivity;
        //need activityBusinessKey-> First GET activity
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(updateActivity = new Activity("updatedGamerTag", Insert activityBusinessKey from DatabaseSetup ActivityType.UCOB, ServerName.MATERIA, "updatedActivityMessage"))
                .when()
                .put("/secured/users/4/activities/1")
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .body("gamerTag", equalTo(updateActivity.getGamerTag()))
                .body("activityType", equalTo(updateActivity.getActivityType().toString()))
                .body("serverName", equalTo(updateActivity.getServerName().toString()))
                .body("activityMessage", equalTo(updateActivity.getActivityMessage()));
    } */
    @Test
    public void deleteActivityHappyPath() {
        given(requestSpecification)
                .when()
                .delete("/secured/users/5/activities/2")
                .then()
                .assertThat()
                .statusCode(204);
    }

}
