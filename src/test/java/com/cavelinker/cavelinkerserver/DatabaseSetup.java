package com.cavelinker.cavelinkerserver;

import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.cavelinker.cavelinkerserver.entities.User;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DatabaseSetup {

    public static void populateUsers(RequestSpecification requestSpecification) {
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(new User("testupdate01@gmail.com", ContactType.INSTAGRAM, "testupdate1"))
                .when()
                .post("/users");
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(new User("testdelete1@gmail.com", ContactType.TWITTER, "testdelete1"))
                .when()
                .post("/users");
    }
}
