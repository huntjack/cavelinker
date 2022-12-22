package com.cavelinker.server.systemtests;

import com.cavelinker.server.enums.ContactType;
import com.cavelinker.server.entities.User;
import com.cavelinker.server.testenvironmentsetup.CaveLinkerIT;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class UserIT extends CaveLinkerIT {

    @Test
    public void postUserHappyPath() {
        User user;
        given(requestSpecification)
                .log().all()
                .header("Content-Type", "application/json")
                .body(user = new User("userpost@gmail.com", ContactType.FACEBOOK, "userpost"))
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
                .put("/users/1")
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
                .delete("/users/2")
                .then()
                .log().all()
                .assertThat()
                .statusCode(204);
    }
}
