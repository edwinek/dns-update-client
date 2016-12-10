package uk.edwinek.dnsupdateclient.client;

import uk.edwinek.dnsupdateclient.model.IpifyResponse;

public interface IpifyClient {

    /**
     * Retrieves the host's current public IP address, via ipify (https://www.ipify.org/).
     *
     * @return an Ipify Response object
     */
    IpifyResponse getCurrentPublicIp();

}
