package za.co.sx.findme.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import za.co.sx.findme.domain.ClickatellRequest;
import za.co.sx.findme.domain.ClickatellResponse;
import za.co.sx.findme.domain.LocationRequest;
import za.co.sx.findme.domain.WhatsappRequest;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class WhatsappService {

    private final WebClient webClient;

    @Value("${meta.api.whatsapp.key}")
    private String apiKey;

    @Value("${meta.api.whatsapp.phoneId}")
    private String phoneId;

    @Value("${meta.api.whatsapp.url}")
    private String url;

    public void sendLocation(LocationRequest request) {

        String message =
                "Hi Mom, I'm safe.\n\n" +
                        "My location:\n" +
                        "https://maps.google.com/?q="
                        + request.getLatitude()
                        + ","
                        + request.getLongitude();

        log.info(message);

        // call Whatapp gateway here
        sendWhatsapp(request.getRecipients(), message);
    }

    public void sendWhatsapp(List<String> cellNumbers, String message) {
        for (String cellNumber : cellNumbers) {
            WhatsappRequest request =
                    WhatsappRequest.builder()
                            .messaging_product("whatsapp")
                            .to(cellNumber)
                            .type("text")
                            .text(
                                    WhatsappRequest.Text.builder()
                                            .body(message)
                                            .build()
                            )
                            .build();
            webClient.post()
                    .uri(String.format("%s/%s/messages", url, phoneId))
                    .header(
                            HttpHeaders.AUTHORIZATION,
                            "Bearer " + apiKey)
                    .bodyValue(request)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        }



    }
}
