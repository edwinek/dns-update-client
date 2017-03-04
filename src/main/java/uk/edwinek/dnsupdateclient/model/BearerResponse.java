package uk.edwinek.dnsupdateclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BearerResponse extends BaseModel {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    private String expiresIn;

    @JsonProperty
    private String scope;

    @JsonProperty("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

}
