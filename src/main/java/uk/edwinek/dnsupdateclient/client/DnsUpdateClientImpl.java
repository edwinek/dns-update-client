package uk.edwinek.dnsupdateclient.client;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import uk.edwinek.dnsupdateclient.model.IpRecord;

@Component
public class DnsUpdateClientImpl implements DnsUpdateClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public String getLastRecordedIp() {

        UriComponents url = UriComponentsBuilder
                .fromHttpUrl(env.getProperty("url.dnsserver"))
                .path("query")
                .queryParam("hostName", env.getProperty("hostName"))
                .build();

        logger.info("Retrieving last recorded IP for host.");
        ResponseEntity<IpRecord> dnsClientRepsonse = restTemplate.getForEntity(url.toUriString(), IpRecord.class);
        String ip = dnsClientRepsonse.getBody().getIp();
        logger.info("Last recorded IP for host is {}", ip);
        return ip;
    }

    @Override
    public void updateIp(String lastRetrievedIp) {
        IpRecord ipRecord = new IpRecord(env.getProperty("hostName"), lastRetrievedIp);

        UriComponents url = UriComponentsBuilder
                .fromHttpUrl(env.getProperty("url.dnsserver"))
                .path("update")
                .build();

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        HttpEntity<?> httpEntity = new HttpEntity<>(ipRecord, requestHeaders);

        logger.info("Updating last recorded IP to {}.", ipRecord.getIp());
        restTemplate.exchange(url.toString(), HttpMethod.POST, httpEntity, IpRecord.class);


    }
}
