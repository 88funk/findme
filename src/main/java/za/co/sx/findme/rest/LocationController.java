package za.co.sx.findme.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import za.co.sx.findme.domain.LocationRequest;
import za.co.sx.findme.services.SmsService;
import za.co.sx.findme.services.WhatsappService;

@RestController
@RequestMapping("/location")
@RequiredArgsConstructor
@Slf4j
public class LocationController {

    private final SmsService smsService;
    private final WhatsappService whatsappService;

    @PostMapping
    public ResponseEntity<Void> sendLocation(
            @RequestBody LocationRequest request) {

//        smsService.sendLocation(request);
        whatsappService.sendLocation(request);

        return ResponseEntity.ok().build();
    }
}
