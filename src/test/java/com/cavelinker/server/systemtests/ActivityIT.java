package com.cavelinker.server.systemtests;

import com.cavelinker.server.entities.Activity;
import com.cavelinker.server.enums.ActivityType;
import com.cavelinker.server.enums.ServerName;
import com.cavelinker.server.testenvironmentsetup.CaveLinkerIT;
import com.cavelinker.server.testenvironmentsetup.DatabaseSetup;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class ActivityIT extends CaveLinkerIT {
    @Test
    public void postActivityHappyPath() {
        Activity postActivity;
        String postActivityBusinessKey = UUID.randomUUID().toString();
        given(requestSpecification)
                .log().all()
                .header("Content-Type", "application/json")
                .body(postActivity = new Activity(postActivityBusinessKey,"PostActivitySuccess", ActivityType.EXTREME_OLD, ServerName.CHAOS, "PostActivitySuccess"))
                .when()
                .post("/users/3/activities")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .body("activityBusinessKey", equalTo(postActivityBusinessKey))
                .body("gamerTag", equalTo(postActivity.getGamerTag()))
                .body("activityType", equalTo(postActivity.getActivityType().toString()))
                .body("serverName", equalTo(postActivity.getServerName().toString()))
                .body("activityMessage", equalTo(postActivity.getActivityMessage()));
    }

    @Test
    public void updateActivityHappyPath() {
        String updateActivityBusinessKey =
                DatabaseSetup.getActivity1()
                .getActivityBusinessKey();
        Activity updateActivity;
        given(requestSpecification)
                .log().all()
                .header("Content-Type", "application/json")
                .body(updateActivity = new Activity(updateActivityBusinessKey,"updatedGamerTag", ActivityType.UCOB, ServerName.MATERIA, "updatedActivityMessage"))
                .when()
                .put("/users/4/activities/1")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .body("activityBusinessKey", equalTo(updateActivity.getActivityBusinessKey()))
                .body("gamerTag", equalTo(updateActivity.getGamerTag()))
                .body("activityType", equalTo(updateActivity.getActivityType().toString()))
                .body("serverName", equalTo(updateActivity.getServerName().toString()))
                .body("activityMessage", equalTo(updateActivity.getActivityMessage()));
    }
    @Test
    public void deleteActivityHappyPath() {
        given(requestSpecification)
                .log().all()
                .when()
                .delete("/users/5/activities/2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(204);
    }

}
