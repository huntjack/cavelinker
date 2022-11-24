package com.cavelinker.cavelinkerserver;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;

public final class EnvFileWriter {
    public static void writeDotEnvFile(String mysqlHost, int mysqlPort, String cavelinkerserverHost, int cavelinkerserverPort) {
        Path path = Paths.get(System.getProperty("~/IdeaProjects/cavelinkerserver/"), ".env");
        System.out.println("Writing port config to " + path);

        List<String> lines = List.of(
                "MYSQL_TEST_HOST=" + mysqlHost,
                "MYSQL_TEST_HOST=" + mysqlPort,
                "CAVELINKERSERVER_TEST_HOST=" + cavelinkerserverHost,
                "CAVELINKERSERVER_TEST_PORT=" + cavelinkerserverPort
        );

        try {
            Files.write(path, lines, CREATE, TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
