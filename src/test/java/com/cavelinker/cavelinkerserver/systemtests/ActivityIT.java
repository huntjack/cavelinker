package com.cavelinker.cavelinkerserver.systemtests;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.enums.ActivityType;
import com.cavelinker.cavelinkerserver.enums.ServerName;
import com.cavelinker.cavelinkerserver.testenvironmentsetup.CaveLinkerIT;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.UUID;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
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
                .post("/secured/users/3/activities")
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
    public void getAndUpdateActivityHappyPath() {
        Response response=
                given(requestSpecification)
                        .log().all()
                        .when()
                        .get("/secured/users/4/activities/1")
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .header("Content-Type", "application/json")
                        .body("gamerTag", equalTo("activityupdate"))
                        .body("activityType", equalTo(ActivityType.UWU.toString()))
                        .body("serverName", equalTo(ServerName.GAIA.toString()))
                        .body("activityMessage", equalTo("activityupdate"))
                        .extract()
                        .response();

        String responseBody = response.getBody().asString();
        JsonPath jsonPathEvaluator = new JsonPath(responseBody);

        String updateActivityBusinessKey= jsonPathEvaluator.getString("activityBusinessKey");
        Activity updateActivity;
        given(requestSpecification)
                .log().all()
                .header("Content-Type", "application/json")
                .body(updateActivity = new Activity(updateActivityBusinessKey,"updatedGamerTag", ActivityType.UCOB, ServerName.MATERIA, "updatedActivityMessage"))
                .when()
                .put("/secured/users/4/activities/1")
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
                .delete("/secured/users/5/activities/2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(204);
    }

}
