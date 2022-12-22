package com.cavelinker.server.systemtests;

import com.cavelinker.server.entities.Schedule;
import com.cavelinker.server.testenvironmentsetup.CaveLinkerIT;
import com.cavelinker.server.testenvironmentsetup.DatabaseSetup;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.*;

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
                        LocalDateTime.of(2022, Month.DECEMBER, 20, 2, 0),
                        ZoneId.of("Asia/Manila")))
                .when()
                .post("/users/6/activities/3/schedules")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .extract()
                .response();

        ResponseBody responseBody = response.getBody();
        Schedule scheduleResponse = responseBody.as(Schedule.class);
        postSchedule.convertToUtc();
        assertEquals(postSchedule.getScheduleBusinessKey(), scheduleResponse.getScheduleBusinessKey());
        assertEquals(postSchedule.getStartTimeUtc(), scheduleResponse.getStartTimeUtc());
        assertEquals(postSchedule.getEndTimeUtc(), scheduleResponse.getEndTimeUtc());
        assertEquals(postSchedule.getUserTimeZone(), scheduleResponse.getUserTimeZone());
    }
    @Test
    public void updateScheduleHappyPath() {
        String updateScheduleBusinessKey = DatabaseSetup.getSchedule1().getScheduleBusinessKey();
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
                .put("/users/7/activities/4/schedules/1")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .extract()
                .response();
        ResponseBody updatedResponseBody = updatedResponse.getBody();
        Schedule updatedSchedule = updatedResponseBody.as(Schedule.class);
        inputSchedule.convertToUtc();
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
                .delete("/users/8/activities/5/schedules/2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(204);

    }
}
