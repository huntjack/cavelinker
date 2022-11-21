import com.cavelinker.cavelinkerserver.enums.ContactType;
import com.cavelinker.cavelinkerserver.model.User;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@Testcontainers
public class UserIT {
    private static Network network = Network.newNetwork();
    @Container
    static MySQLContainer mysqlDB = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.31-debian"))
            .withNetwork(network)
            .withNetworkAliases("mysqlDB");
    @Container
    static GenericContainer payara_container = new GenericContainer(DockerImageName.parse("payara/micro:5.2022.4-jdk11"))
            .withExposedPorts(8080)
            .withCopyFileToContainer(MountableFile.forClasspathResource("target/cavelinkerserver/WEB-INF/lib/mysql-connector-j-8.0.31.jar"), "/opt/payara/mysql-connector-j-8.0.31.jar")
            .withCopyFileToContainer(MountableFile.forHostPath("target/cavelinkerserver.war"), "/opt/payara/cavelinkerserver.war")
            .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
            .withCommand("--addlibs /opt/payara/mysql-connector-j-8.0.31.jar --deploy /opt/payara/cavelinkerserver.war --noCluster")
            .dependsOn(mysqlDB)
            .withNetwork(network)
            .withEnv("DB_USER", mysqlDB.getUsername())
            .withEnv("DB_PASSWORD", mysqlDB.getPassword())
            .withEnv("DB_JDBC_URL", "jdbc:mysql://mysql:3306/" + mysqlDB.getDatabaseName());


    private static RequestSpecification requestSpecification;

    @Test
    public void PostHappyPath() {
        User user;
        given()
                .header("Content-Type", "application/json")
                .body(user = new User("hunt.jack01@gmail.com", "myPassword", ContactType.FACEBOOK, "Eric Blackwood"))
                .when()
                .post("http://" + payara_container.getHost() + ":" + payara_container.getMappedPort(8080) + "/cavelinkerserver/api/users")
                .then()
                .statusCode(204)
                .body("email", equalTo(user.getEmail()))
                .body("password", equalTo(user.getPassword()))
                .body("contactType", equalTo(user.getContactType()))
                .body("contactUserName", equalTo(user.getContactUserName()));
    }


}
