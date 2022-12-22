package com.cavelinker.server.testenvironmentsetup;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Paths;

public abstract class CaveLinkerIT {
    protected static Network network = Network.newNetwork();
    protected static MySQLContainer mysql;
    protected static GenericContainer cavelinker;
    protected static RequestSpecification requestSpecification;

    static {
        mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.31-debian"))
                .withNetwork(network)
                .withNetworkAliases("mysql");

        cavelinker = new GenericContainer(new ImageFromDockerfile()
                .withDockerfile(Paths.get("/home/jack/IdeaProjects/cavelinker/Dockerfile")))
                .withExposedPorts(8080)
                .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
                .dependsOn(mysql)
                .withNetwork(network)
                .withNetworkAliases("cavelinker")
                .withEnv("DB_USER", mysql.getUsername())
                .withEnv("DB_PASSWORD", mysql.getPassword())
                .withEnv("DB_JDBC_URL", "jdbc:mysql://mysql:3306/" + mysql.getDatabaseName());

        mysql.start();
        cavelinker.start();

        String baseUri = "http://" + cavelinker.getHost() + ":" + cavelinker.getMappedPort(8080) +
                "/api";
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .build();

        DatabaseSetup.populateData(baseUri);
    }
}
