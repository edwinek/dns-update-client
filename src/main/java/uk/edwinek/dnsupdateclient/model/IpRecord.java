package uk.edwinek.dnsupdateclient.model;

public class IpRecord extends BaseModel {

    private String hostName;
    private String ip;

    public IpRecord(String hostName, String ip) {
        this.hostName = hostName;
        this.ip = ip;
    }

    public IpRecord() {
    }

    public String getIp() {
        return ip;
    }

    public String getHostName() {
        return hostName;
    }
}
