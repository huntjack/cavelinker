package com.cavelinker.cavelinkerserver.systemtests;

import com.cavelinker.cavelinkerserver.entities.Schedule;
import com.cavelinker.cavelinkerserver.testenvironmentsetup.CaveLinkerIT;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class ScheduleIT extends CaveLinkerIT {
    @Test
    public void postScheduleHappyPath() {
        Schedule postSchedule;
        String postScheduleBusinessKey = UUID.randomUUID().toString();
        Response response = given(requestSpecification)
                .log().all()
                .header("Content-Type", "application/json")
                .body(postSchedule = new Schedule(
                        postScheduleBusinessKey,
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 22, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 02, 0),
                        ZoneId.of("Asia/Manila")))
                .when()
                .post("/secured/users/6/activities/3/schedules")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .extract()
                .response();

        ResponseBody responseBody = response.getBody();
        Schedule scheduleResponse = responseBody.as(Schedule.class);
        assertEquals(postScheduleBusinessKey, scheduleResponse.getScheduleBusinessKey());
        assertEquals(postSchedule.getStartTimeUtc(), scheduleResponse.getStartTimeUtc());
        assertEquals(postSchedule.getEndTimeUtc(), scheduleResponse.getEndTimeUtc());
        assertEquals(postSchedule.getUserTimeZone(), scheduleResponse.getUserTimeZone());
    }
    @Test
    public void getAndUpdateScheduleHappyPath() {
        Response initialResponse =
                given(requestSpecification)
                        .log().all()
                        .when()
                        .get("/secured/users/7/activities/4/schedules/1")
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .header("Content-Type", "application/json")
                        .extract()
                        .response();
        //Check GET Response
        ResponseBody initialResponseBody = initialResponse.getBody();
        Schedule initialSchedule = initialResponseBody.as(Schedule.class);
        assertEquals(LocalDateTime.of(2022, Month.DECEMBER, 19, 16, 0), initialSchedule.getStartTimeUtc());
        assertEquals(LocalDateTime.of(2022, Month.DECEMBER, 19, 21, 0), initialSchedule.getEndTimeUtc());
        assertEquals(ZoneId.of("America/Los_Angeles"), initialSchedule.getUserTimeZone());
        //Get random scheduleBusinessKey from response
        String responseBody = initialResponseBody.asString();
        JsonPath jsonPathEvaluator = new JsonPath(responseBody);
        String updateScheduleBusinessKey = jsonPathEvaluator.getString("scheduleBusinessKey");

        Schedule inputSchedule;
        Response updatedResponse =
        given(requestSpecification)
                .log().all()
                .header("Content-Type", "application/json")
                .body(inputSchedule = new Schedule(updateScheduleBusinessKey,
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 18, 0),
                        LocalDateTime.of(2022, Month.DECEMBER, 19, 22, 0),
                        ZoneId.of("America/Los_Angeles")))
                .when()
                .put("/secured/users/7/activities/4/schedules/1")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .extract()
                .response();
        ResponseBody updatedResponseBody = updatedResponse.getBody();
        Schedule updatedSchedule = updatedResponseBody.as(Schedule.class);
        assertEquals(inputSchedule.getScheduleBusinessKey(), updatedSchedule.getScheduleBusinessKey());
        assertEquals(inputSchedule.getStartTimeUtc(), updatedSchedule.getStartTimeUtc());
        assertEquals(inputSchedule.getEndTimeUtc(), updatedSchedule.getEndTimeUtc());
        assertEquals(inputSchedule.getUserTimeZone(), updatedSchedule.getUserTimeZone());
    }
    @Test
    public void deleteScheduleHappyPath() {
        given(requestSpecification)
                .log().all()
                .when()
                .delete("/secured/users/8/activities/5/schedules/2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(204);

    }
}
