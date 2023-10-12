package com.allin.filmface.choiceContents.auth;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Auth {

    @Value("${youtube.api.key}")
    private String API_KEY;

    public static final HttpTransport HTTP_TRANSPORT;
    public static final JsonFactory JSON_FACTORY;

    static {
        try {
            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            JSON_FACTORY = JacksonFactory.getDefaultInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getApiKey() {
        return API_KEY;
    }
}
