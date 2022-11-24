package com.cavelinker.cavelinkerserver;

import jakarta.ws.rs.core.UriBuilder;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;

import java.net.URI;

public class ConfigGenerator {

    private URI buildServerUri() {
        Config config = ConfigProvider.getConfig();
        String serverHost = config.getValue("cavelinkerserver.test.host", String.class);
        String serverPort = config.getValue("cavelinkerserver.test.port", String.class);
        return UriBuilder.fromUri("http://{host}:{port}/cavelinkerserver/api")
                .build(serverHost, serverPort);
    }


}
