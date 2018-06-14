package com.dhr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PropertiesConfig {

    @Value("${server.port}")
    private String serverPort;

    @Value("${backendHost}")
    private String backendHost;

    @Value("${recordings.path}")
    private String recordingsPath;

    public String getBackendHost() {
        return backendHost;
    }

    public String getServerPort() {
        return serverPort;
    }

    public String getRecordingsPath() {
        return recordingsPath;
    }

}
