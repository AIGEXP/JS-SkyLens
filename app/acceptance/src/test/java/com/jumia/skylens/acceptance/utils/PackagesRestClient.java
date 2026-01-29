package com.jumia.skylens.acceptance.utils;

import com.jumia.skylens.acceptance.dto.PackageResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.LocalDate;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PackagesRestClient {

    private static final String GET_STOPS_URL = "/api/partners/{partnerSid}/packages";

    private static final String PUBLISH_LAST_MILE_STOPS = "/api/stops/publish";

    private final RestClient restClient;

    public ResponseEntity<PackageResponseDTO[]> fetchStopPackages(UUID partnerSid) {

        return restClient.get()
                .uri(GET_STOPS_URL, partnerSid)
                .header(HttpHeaders.AUTHORIZATION, AclTokens.VALID_TOKEN)
                .retrieve()
                .toEntity(PackageResponseDTO[].class);
    }

    public void publishLastMileStops() {

        final LocalDate yesterday = LocalDate.now().minusDays(1);

        final String json = "{ \"network\": \"NG\", \"date\": \"%s\" } ".formatted(yesterday.toString());

        restClient.post()
                .uri(PUBLISH_LAST_MILE_STOPS)
                .contentType(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, AclTokens.VALID_TOKEN)
                .body(json)
                .retrieve()
                .toBodilessEntity();
    }
}
