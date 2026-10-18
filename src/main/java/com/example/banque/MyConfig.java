package com.example.banque;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig extends ResourceConfig {
    public MyConfig() {
        register(CompteRestJaxRSAPI.class);
    }
}
