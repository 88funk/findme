package za.co.sx.findme.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import za.co.sx.findme.domain.ClickatellRequest;
import za.co.sx.findme.domain.ClickatellResponse;
import za.co.sx.findme.domain.LocationRequest;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SmsService {

    private final WebClient webClient;

    @Value("${clickatell.api.key}")
    private String apiKey;

    public void sendLocation(LocationRequest request) {

        String message =
                "Hi Mom, I'm safe.\n\n" +
                        "My location:\n" +
                        "https://maps.google.com/?q="
                        + request.getLatitude()
                        + ","
                        + request.getLongitude();

        log.info(message);

        // call SMS gateway here
        sendSms(request.getRecipients(), message);
    }

    public ClickatellResponse sendSms(List<String> cellNumbers, String message) {

        ClickatellRequest request = ClickatellRequest.builder()
                .text(message)
                .to(cellNumbers)
                .build();
        ClickatellResponse response = null;

        try {

             response =
                    webClient.post()
                            .uri("https://api.clickatell.com/rest/message")
                            .header("X-Version", "1")
                            .header(HttpHeaders.AUTHORIZATION,
                                    "Bearer " + apiKey)
                            .bodyValue(request)
                            .retrieve()
                            .bodyToMono(ClickatellResponse.class)
                            .block();

            log.info("Response: {}", response);

        } catch (Exception e) {
            log.error("SMS failed", e);
        }
        return response;
    }
}
