package com.cavelinker.cavelinkerserver.systemtests;

import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.cavelinker.cavelinkerserver.entities.User;
import com.cavelinker.cavelinkerserver.testenvironmentsetup.CaveLinkerIT;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

public class UserIT extends CaveLinkerIT {

    @Test
    public void postUserHappyPath() {
        User user;
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(user = new User("testpost01@gmail.com", ContactType.FACEBOOK, "post1contact"))
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .body("email", equalTo(user.getEmail()))
                .body("contactType", equalTo(ContactType.FACEBOOK.toString()))
                .body("contactUserName", equalTo(user.getContactUserName()));
    }
    @Test
    public void updateUserHappyPath() {
        User user;
        given(requestSpecification)
                .header("Content-Type", "application/json")
                .body(user = new User("updatesuccess@gmail.com", ContactType.DISCORD, "newUpdateContact"))
                .when()
                .put("/secured/users/1")
                .then()
                .assertThat()
                .statusCode(200)
                .header("Content-Type", "application/json")
                .body("email", equalTo(user.getEmail()))
                .body("contactType", equalTo(ContactType.DISCORD.toString()))
                .body("contactUserName", equalTo(user.getContactUserName()));
    }
    @Test
    public void deleteUserHappyPath() {
        given(requestSpecification)
                .when()
                .delete("/secured/users/2")
                .then()
                .assertThat()
                .statusCode(204);
    }
}
