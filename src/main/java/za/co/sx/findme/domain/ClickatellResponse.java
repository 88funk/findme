package za.co.sx.findme.domain;

import lombok.Data;

import java.util.List;

@Data
public class ClickatellResponse {

    private Data data;

    @lombok.Data
    public static class Data {
        private List<Message> message;
    }

    @lombok.Data
    public static class Message {
        private boolean accepted;
        private String to;
        private String apiMessageId;
    }
}