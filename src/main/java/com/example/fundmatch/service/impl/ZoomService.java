package com.example.fundmatch.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class ZoomService {

    private final String ZOOM_API_URL = "https://api.zoom.us/v2/users/me/meetings";
    private final String ZOOM_OAUTH_TOKEN_URL = "https://zoom.us/oauth/token";

    @Value("${zoom.client-id}")
    private String clientId;

    @Value("${zoom.client-secret}")
    private String clientSecret;

    @Value("${zoom.account-id}")
    private String accountId;

    private final RestTemplate restTemplate;

    // Constructor injection
    public ZoomService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Méthode pour récupérer un token d'accès OAuth
    private String getAccessToken() {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(clientId, clientSecret);
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            HttpEntity<String> request = new HttpEntity<>("grant_type=account_credentials&account_id=" + accountId, headers);
            ResponseEntity<Map> response = restTemplate.exchange(ZOOM_OAUTH_TOKEN_URL, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("access_token");
            } else {
                throw new RuntimeException("Failed to obtain Zoom access token");
            }
        } catch (RestClientException e) {
            throw new RuntimeException("Error while getting Zoom access token", e);
        }
    }

    // Méthode pour créer une réunion Zoom
    public Map<String, Object> createMeeting(String topic, LocalDateTime startTime, int durationMinutes) {
        try {
            String accessToken = getAccessToken();

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("topic", topic);
            requestBody.put("type", 2); // Scheduled meeting

            // Format date as ISO-8601
            String formattedTime = startTime.atZone(ZoneOffset.UTC)
                    .format(DateTimeFormatter.ISO_INSTANT);
            requestBody.put("start_time", formattedTime);

            requestBody.put("duration", durationMinutes);
            requestBody.put("timezone", "UTC");

            HttpEntity<Map<String, Object>> request = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.exchange(
                    ZOOM_API_URL, HttpMethod.POST, request, Map.class);

            return response.getBody();
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Zoom meeting", e);
        }
    }
}