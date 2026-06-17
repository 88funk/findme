package za.co.sx.findme.domain;

import lombok.Data;

import java.util.List;

@Data
public class LocationRequest {

    private Double latitude;
    private Double longitude;
    private List<String> recipients;
}