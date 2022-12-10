package com.cavelinker.cavelinkerserver.testenvironmentsetup;

import com.cavelinker.cavelinkerserver.entities.Activity;
import com.cavelinker.cavelinkerserver.enums.ActivityType;
import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.cavelinker.cavelinkerserver.entities.User;
import com.cavelinker.cavelinkerserver.enums.ServerName;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

public class DatabaseSetup {

    private static Client client = ClientBuilder.newClient();
    public static void populateData(String baseUri) {
        WebTarget baseTarget = client.target(baseUri);
        WebTarget userTarget = baseTarget.path("/users");
        WebTarget activityPostTarget = userTarget.path("{userId}/activities");
        //User 1: Test User Update
        Response userResponse1 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("testuserupdate@gmail.com", ContactType.INSTAGRAM, "testuserupdate")));
        //User 2: Test User Delete
        Response userResponse2 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("testuserdelete@gmail.com", ContactType.TWITTER, "testuserdelete")));
        //User 3: Test Activity Post
        Response userResponse3 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("testactivitypost@gmail.com", ContactType.DISCORD, "testactivitypost")));
        //User 4: Test Activity Update
        Response userResponse4 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("testactivityupdate@gmail.com", ContactType.INSTAGRAM, "testactivityupdate")));
        //User 4->Activity 1: Test Activity Update
        Response activityResponse1 = activityPostTarget
                .resolveTemplate("userId", "4")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity("testUpdateGamerTag", UUID.randomUUID().toString(), ActivityType.UWU,
                        ServerName.GAIA, "testUpdateActivityMessage")));
        //User 5: Test Activity Delete
        Response userResponse5 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("testactivitydelete@gmail.com", ContactType.FACEBOOK, "testactivitydelete")));
        //User 5->Activity 2: Test Activity Delete
        Response activityResponse2 = activityPostTarget
                .resolveTemplate("userId", "5")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity("testDeleteGamerTag", UUID.randomUUID().toString(), ActivityType.DSU, ServerName.DYNAMIS, "testDeleteActivityMessage")));
    }
}
