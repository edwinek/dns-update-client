package uk.edwinek.dnsupdateclient.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.edwinek.dnsupdateclient.client.DnsUpdateClient;
import uk.edwinek.dnsupdateclient.client.IpifyClient;
import uk.edwinek.dnsupdateclient.model.IpRecord;
import uk.edwinek.dnsupdateclient.model.IpifyResponse;

@Component
public class DnsServiceImpl implements DnsService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IpifyClient ipifyClient;

    @Autowired
    private DnsUpdateClient dnsUpdateClient;

    private String lastRetrievedIp;

    @Override
    public void checkAndUpdate() {
        IpifyResponse currentPublicIp = ipifyClient.getCurrentPublicIp();
        String currentIp = currentPublicIp.getIp();
        updateLastRetrievedIp(currentIp);
    }

    @Override
    public String getLastRetrievedIp() {
        return lastRetrievedIp;
    }

    @Override
    public void setLastRetrievedIp(String lastRetrievedIp) {
        this.lastRetrievedIp = lastRetrievedIp;
    }

    private void updateLastRetrievedIp(String currentIp) {
        if (isIp(currentIp)) {
            if (lastRetrievedIp == null) {
                logger.info("No IP configured currently in client, so using retrieved IP of {}.", currentIp);
                lastRetrievedIp = currentIp;
                updateAtServerIfNeeded(lastRetrievedIp);
            } else if (!lastRetrievedIp.equals(currentIp)) {
                logger.info("IP appears to have changed from last retrieved value of {} to {}.", lastRetrievedIp, currentIp);
                lastRetrievedIp = currentIp;
                updateAtServerIfNeeded(lastRetrievedIp);
            } else {
                logger.info("IP matches the last retrieved value of {}.", lastRetrievedIp);
            }
        } else {
            logger.error("Current IP looks invalid {}.", currentIp);
        }
    }

    private void updateAtServerIfNeeded(String lastRetrievedIp) {
        String lastRecordedIp = dnsUpdateClient.getLastRecordedIp();

        if (lastRetrievedIp.equals(lastRecordedIp)) {
            logger.info("Last recorded IP at server matches {}, so no need to update", lastRecordedIp);
        } else {
            logger.info("Updating IP at server to from {} to {}.", lastRecordedIp, lastRetrievedIp);
            dnsUpdateClient.updateIp(lastRetrievedIp);
        }

    }

    private boolean isIp(String ipString) {
        return ipString.matches("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");
    }
}
