package service.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class RequestDto {
    private String type;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    String issuer;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }
}
