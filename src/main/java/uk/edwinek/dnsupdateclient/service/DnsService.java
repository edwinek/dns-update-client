package uk.edwinek.dnsupdateclient.service;

public interface DnsService {

    /**
     * Retrieves the host's current IP address and checks it against the most recently retrieved on.
     * Records the IP if it has changed, and updated the configured DNS Update Service instance.
     *
     */
    void checkAndUpdate();

    /**
     * Getter for Last Retrieved IP.
     *
     * @return an IP string
     */
    String getLastRetrievedIp();

    /**
     * Setter for Last Retrieved IP (used for unit testing).
     *
     * @param lastRetrievedIp an IP String
     */
    void setLastRetrievedIp(String lastRetrievedIp);
}
