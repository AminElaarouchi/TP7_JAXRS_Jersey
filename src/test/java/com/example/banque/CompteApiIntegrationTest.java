package com.example.banque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CompteApiIntegrationTest {
    @Value("${local.server.port}") private int port;
    private final HttpClient client = HttpClient.newHttpClient();

    @Test void supportsJsonXmlAndCrud() throws Exception {
        String base = "http://localhost:" + port + "/banque/comptes";
        HttpResponse<String> json = client.send(HttpRequest.newBuilder(URI.create(base))
                .header("Accept", "application/json").GET().build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(200, json.statusCode());
        assertTrue(json.body().contains("EPARGNE"));

        HttpResponse<String> xml = client.send(HttpRequest.newBuilder(URI.create(base))
                .header("Accept", "application/xml").GET().build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(200, xml.statusCode());
        assertTrue(xml.body().contains("<comptes>"));

        HttpResponse<String> created = client.send(HttpRequest.newBuilder(URI.create(base))
                .header("Content-Type", "application/json").header("Accept", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"solde\":5000,\"dateCreation\":\"2024-10-30\",\"type\":\"COURANT\"}"))
                .build(), HttpResponse.BodyHandlers.ofString());
        assertEquals(201, created.statusCode());
        assertTrue(created.body().contains("COURANT"));
    }
}
