package com.cavelinker.cavelinkerserver.systemtests;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.enums.ActivityType;
import com.cavelinker.cavelinkerserver.enums.ServerName;
import com.cavelinker.cavelinkerserver.testenvironmentsetup.cavelinkerIT;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@Testcontainers
public class ActivityIT extends cavelinkerIT {

    @Test
    public void postActivityHappyPath() {
        Activity activity;
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(activity = new Activity("postGamerTag", ActivityType.EXTREME_OLD, ServerName.CHAOS, "postActivityMessage"))
                .when()
                .post("/secured/users/3/activities").then()
                .assertThat()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .body("gamerTag", equalTo(activity.getGamerTag()))
                .body("activityType", equalTo(activity.getActivityType().toString()))
                .body("serverName", equalTo(activity.getServerName().toString()))
                .body("activityMessage", equalTo(activity.getActivityMessage()));
    }
    @Test
    public void deleteActivityHappyPath() {
        given(requestSpecification)
                .when()
                .delete("/secured/users/4/activities/4")
                .then()
                .assertThat()
                .statusCode(204);
    }

}
