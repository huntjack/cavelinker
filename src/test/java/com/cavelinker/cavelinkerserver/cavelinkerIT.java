package com.cavelinker.cavelinkerserver;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.DockerImageName;
import java.nio.file.Paths;

public class cavelinkerIT {
    private static Network network = Network.newNetwork();

    static MySQLContainer mysql;
    static GenericContainer cavelinkerserver;
    static RequestSpecification requestSpecification;

    @BeforeAll
    static void setup() {
        mysql=new MySQLContainer<>(DockerImageName.parse("mysql:8.0.31-debian"))
                .withNetwork(network)
                .withNetworkAliases("mysql");

        cavelinkerserver= new GenericContainer(new ImageFromDockerfile()
                .withDockerfile(Paths.get("/home/jack/IdeaProjects/cavelinkerserver/Dockerfile")))
                .withExposedPorts(8080)
                .waitingFor(Wait.forHttp("/api/application.wadl").forStatusCode(200))
                .dependsOn(mysql)
                .withNetwork(network)
                .withNetworkAliases("cavelinkerserver")
                .withEnv("DB_USER", mysql.getUsername())
                .withEnv("DB_PASSWORD", mysql.getPassword())
                .withEnv("DB_JDBC_URL", "jdbc:mysql://mysql:3306/" + mysql.getDatabaseName());

        mysql.start();
        cavelinkerserver.start();

        String baseUri = "http://" + cavelinkerserver.getHost() + ":" + cavelinkerserver.getMappedPort(8080) +
                "/api";
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .build();

        DatabaseSetup.populateUsers(requestSpecification);
    }
}
