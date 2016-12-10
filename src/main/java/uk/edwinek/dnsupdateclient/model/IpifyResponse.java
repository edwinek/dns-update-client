package uk.edwinek.dnsupdateclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IpifyResponse extends BaseModel {

    @JsonProperty
    private String ip;

    public String getIp() {
        return ip;
    }
}
