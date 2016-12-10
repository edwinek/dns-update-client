package uk.edwinek.dnsupdateclient.client;

public interface DnsUpdateClient {

    /**
     * Retrieve the last known IP that was saved to the server
     *
     * @return an IP
     */
    String getLastRecordedIp();

    /**
     * Updates the IP at the server for the host (uses upsert)
     *
     * @param lastRetrievedIp the new IP
     */
    void updateIp(String lastRetrievedIp);
}
