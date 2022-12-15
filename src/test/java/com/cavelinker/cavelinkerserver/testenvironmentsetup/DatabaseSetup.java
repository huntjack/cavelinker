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
import org.glassfish.jersey.logging.LoggingFeature;

import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseSetup {

    private static Client client;
    private static Logger logger;

    static LoggingFeature logging() {
        logger = Logger.getLogger(DatabaseSetup.class.getName());
        return new LoggingFeature(logger, Level.INFO, LoggingFeature.Verbosity.PAYLOAD_TEXT, null);
    }
    public static void populateData(String baseUri) {
        client = ClientBuilder.newClient().register(logging());
        WebTarget baseTarget = client.target(baseUri);
        WebTarget userTarget = baseTarget.path("/users");
        WebTarget activityPostTarget = baseTarget.path("/secured/users/{userId}/activities");
        //User 1: Test User Update
        Response userResponse1 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("userupdate@gmail.com", ContactType.INSTAGRAM, "userupdate")));
        //User 2: Test User Delete
        Response userResponse2 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("userdelete@gmail.com", ContactType.TWITTER, "userdelete")));
        //User 3: Test Activity Post
        Response userResponse3 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("activitypost@gmail.com", ContactType.DISCORD, "activitypost")));
        //User 4: Test Activity Update
        Response userResponse4 = userTarget
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new User("activityupdate@gmail.com", ContactType.INSTAGRAM, "activityupdate")));
        //User 4->Activity 1: Test Activity Update
        Response activityResponse1 = activityPostTarget
                .resolveTemplate("userId", "4")
                .request()
                .header("Content-Type", "application/json")
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),"activityupdate", ActivityType.UWU,
                        ServerName.GAIA, "activityupdate")));
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
                .post(Entity.json(new Activity(UUID.randomUUID().toString(),"DeleteGamerTag", ActivityType.DSU,
                        ServerName.DYNAMIS, "DeleteActivityMessage")));
    }
}
