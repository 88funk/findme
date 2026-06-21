package za.co.sx.findme.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WhatsappRequest {

    private String messaging_product;
    private String recipient_type;
    private String to;
    private String type;
    private Text text;

    @Data
    @Builder
    public static class Text {
        private Boolean preview_url;
        private String body;
    }
}
