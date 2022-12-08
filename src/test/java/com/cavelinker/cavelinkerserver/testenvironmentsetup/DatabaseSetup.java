package com.cavelinker.cavelinkerserver.testenvironmentsetup;

import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.cavelinker.cavelinkerserver.entities.User;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class DatabaseSetup {

    public static void populateData(RequestSpecification requestSpecification) {
        //User 1
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(new User("testuserupdate@gmail.com", ContactType.INSTAGRAM, "testuserupdate"))
                .when()
                .post("/users");
        //User 2
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(new User("testuserdelete@gmail.com", ContactType.TWITTER, "testuserdelete"))
                .when()
                .post("/users");
        //User 3
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(new User("testactivitypost@gmail.com", ContactType.DISCORD, "testactivitypost"))
                .when()
                .post("/users");
        //User 4
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(new User("testactivitydelete@gmail.com", ContactType.FACEBOOK, "testactivitydelete"))
                .when()
                .post("/users");
    }
}
