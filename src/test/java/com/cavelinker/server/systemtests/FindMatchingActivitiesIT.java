package com.cavelinker.server.systemtests;

import com.cavelinker.server.testenvironmentsetup.CaveLinkerIT;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FindMatchingActivitiesIT extends CaveLinkerIT {

    @Test
    public void findMatchingActivitiesHappyPath() {
        given(requestSpecification)
                .log().all()
                .when()
                .get("/users/9/activities/6")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("size()", is(3))
                .body("find {it.activityId == 6}.schedules.scheduleId", containsInAnyOrder(3,4,5))
                .body("find {it.activityId == 7}.schedules.scheduleId", containsInAnyOrder(6,7,8))
                .body("find {it.activityId == 10}.schedules.scheduleId", containsInAnyOrder(15,16,17));
    }

}
