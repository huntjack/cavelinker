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
                .log().all()
                .header("Content-Type", "application/json")
                .body(user = new User("userupdatesuccess@gmail.com", ContactType.FACEBOOK, "userupdatesuccess"))
                .when()
                .post("/users")
                .then()
                .log().all()
                .assertThat()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .body("email", equalTo(user.getEmail()))
                .body("contactType", equalTo(user.getContactType().toString()))
                .body("contactUserName", equalTo(user.getContactUserName()));
    }
    @Test
    public void updateUserHappyPath() {
        User user;
        given(requestSpecification)
                .log().all()
                .header("Content-Type", "application/json")
                .body(user = new User("userupdate@gmail.com", ContactType.DISCORD, "updatedContact"))
                .when()
                .put("/secured/users/1")
                .then()
                .log().all()
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
                .log().all()
                .when()
                .delete("/secured/users/2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(204);
    }
}
