import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.cavelinker.cavelinkerserver.model.User;

import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Path;
import java.nio.file.Paths;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Testcontainers
public class UserIT {
    private static Network network = Network.newNetwork();
    private static Path path;
    @Container
    static MySQLContainer mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.31-debian"))
            .withNetwork(network)
            .withNetworkAliases("mysql");
    @Container
    static GenericContainer payara_container = new GenericContainer(new ImageFromDockerfile()
            .withDockerfile(path=Paths.get("/home/jack/IdeaProjects/cavelinkerserver/Dockerfile")))
            .withExposedPorts(8080)
            // .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
            .dependsOn(mysql)
            .withNetwork(network)
            .withEnv("DB_USER", mysql.getUsername())
            .withEnv("DB_PASSWORD", mysql.getPassword())
            .withEnv("DB_JDBC_URL", "jdbc:mysql://mysql:3306/" + mysql.getDatabaseName());

    @BeforeEach
    void setup() throws InterruptedException {
        try {
            Thread.sleep(30000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        Response response = given()
                .header("Content-Type", "application/json")
                .body(user = new User("hunt.jack01@gmail.com", "myPassword", ContactType.FACEBOOK, "Eric Blackwood"))
                .when()
                .post("http://" + payara_container.getHost() + ":" + payara_container.getMappedPort(8080) + "/cavelinkerserver/api/users");

        ContactType contactType=
                response.then()
                .assertThat()
                .statusCode(201)
                .header("Content-Type", "application/json")
                .body("email", equalTo(user.getEmail()))
                .body("password", equalTo(user.getPassword()))
                .body("contactUserName", equalTo(user.getContactUserName()))
                .extract().path("contactType");
                assertThat(contactType, equalTo(ContactType.FACEBOOK));
    }


}
