package com.cavelinker.cavelinkerserver;

import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.lifecycle.Startables;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.cavelinker.cavelinkerserver.EnvFileWriter.writeDotEnvFile;

public class ContainerEnvironment {
    private static Network network = Network.newNetwork();
    private static Path path;

    static MySQLContainer mysql = new MySQLContainer<>(DockerImageName.parse("mysql:8.0.31-debian"))
            .withNetwork(network)
            .withNetworkAliases("mysql");
    static GenericContainer cavelinkerserver = new GenericContainer(new ImageFromDockerfile()
            .withDockerfile(path= Paths.get("/home/jack/IdeaProjects/cavelinkerserver/Dockerfile")))
            .withExposedPorts(8080)
            .waitingFor(Wait.forLogMessage(".* Payara Micro .* ready in .*\\s", 1))
            .dependsOn(mysql)
            .withNetwork(network)
            .withNetworkAliases("cavelinkerserver")
            .withEnv("DB_USER", mysql.getUsername())
            .withEnv("DB_PASSWORD", mysql.getPassword())
            .withEnv("DB_JDBC_URL", "jdbc:mysql://mysql:3306/" + mysql.getDatabaseName());

    @Test
    void startContainers() throws IOException {

        Startables.deepStart(mysql, cavelinkerserver).join();

        String mysqlHost = mysql.getHost();
        int mysqlPort = mysql.getMappedPort(8080);
        String cavelinkerserverHost = cavelinkerserver.getHost();
        int cavelinkerserverPort = cavelinkerserver.getMappedPort(8080);

        writeDotEnvFile(mysqlHost, mysqlPort, cavelinkerserverHost, cavelinkerserverPort);

        String uri = "http://" + cavelinkerserverHost + ":" + cavelinkerserverPort + "/cavelinkerserver/api";
        System.out.println("The cavelinkerserver base URI is: " + uri);
        System.out.println("\nContainers started. Press any key or kill process to stop");
        System.in.read();
    }
}
