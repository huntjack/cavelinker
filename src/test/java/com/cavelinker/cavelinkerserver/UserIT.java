package com.cavelinker.cavelinkerserver;

import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.cavelinker.cavelinkerserver.model.User;

import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

@Testcontainers
public class UserIT {

    @Test
    public void getHappyPath() {
        given()
                .when()
                .get("http://" + payara_container.getHost() + ":" + payara_container.getMappedPort(8080) + "/cavelinkerserver/api/users/test")
                .then()
                .assertThat()
                .statusCode(200);
    }


    @Test
    public void PostHappyPath() {
        User user;
        given()
                .header("Content-Type", "application/json")
                .body(user = new User("hunt.jack01@gmail.com", "myPassword", ContactType.FACEBOOK, "Eric Blackwood"))
                .when()
                .post("http://" + payara_container.getHost() + ":" + payara_container.getMappedPort(8080) + "/cavelinkerserver/api/users")
                .then()
                .assertThat()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .body("email", equalTo(user.getEmail()))
                .body("password", equalTo(user.getPassword()))
                .body("contactType", equalTo(ContactType.FACEBOOK.toString()))
                .body("contactUserName", equalTo(user.getContactUserName()));
    }


}
