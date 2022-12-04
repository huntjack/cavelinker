package com.cavelinker.cavelinkerserver;

import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.cavelinker.cavelinkerserver.entities.User;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Testcontainers
public class UserIT extends cavelinkerIT {

    @Test
    public void postHappyPath() {
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
    public void updateHappyPath() {
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
    public void deleteHappyPath() {
        given(requestSpecification)
                .when()
                .delete("/secured/users/2")
                .then()
                .assertThat()
                .statusCode(204);
    }
}
