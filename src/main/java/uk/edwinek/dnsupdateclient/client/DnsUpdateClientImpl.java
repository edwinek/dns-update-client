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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import uk.edwinek.dnsupdateclient.model.BearerResponse;
import uk.edwinek.dnsupdateclient.model.IpRecord;

import java.util.Base64;

@Component
public class DnsUpdateClientImpl implements DnsUpdateClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    private String oAuthToken;

    @Override
    public String getLastRecordedIp() {
        requestBearerToken();
        UriComponents url = UriComponentsBuilder
                .fromHttpUrl(env.getProperty("url.dnsserver"))
                .path("query")
                .queryParam("hostName", env.getProperty("hostName"))
                .build();
        HttpEntity<?> httpEntity = new HttpEntity<>(requestOAuthResourceHeaders());
        logger.info("Retrieving last recorded IP for host.");
        ResponseEntity<IpRecord> dnsClientResponse = restTemplate.exchange(url.toUriString(), HttpMethod.GET, httpEntity, IpRecord.class);
        String ip = dnsClientResponse.getBody().getIp();
        logger.info("Last recorded IP for host is {}", ip);
        return ip;
    }

    @Override
    public void updateIp(String lastRetrievedIp) {
        requestBearerToken();
        IpRecord ipRecord = new IpRecord(env.getProperty("hostName"), lastRetrievedIp);

        UriComponents url = UriComponentsBuilder
                .fromHttpUrl(env.getProperty("url.dnsserver"))
                .path("update")
                .build();

        HttpEntity<?> httpEntity = new HttpEntity<>(ipRecord, requestOAuthResourceHeaders());

        logger.info("Updating last recorded IP to {}.", ipRecord.getIp());
        restTemplate.exchange(url.toString(), HttpMethod.POST, httpEntity, IpRecord.class);
    }

    private String basicAuthenticationString() {
        String credentials = env.getProperty("basic.user") + ":" + env.getProperty("basic.password");
        String encodedAuthorization = Base64.getEncoder().encodeToString(credentials.getBytes());
        return "Basic " + encodedAuthorization;
    }

    private void requestBearerToken() {
        logger.info("Requesting bearer token.");
        HttpEntity<?> httpEntity = new HttpEntity<>(requestBearerTokenBody(), requestBearerTokenHeaders());
        BearerResponse getBearerResponse
                = restTemplate.exchange(env.getProperty("url.authentication"), HttpMethod.POST, httpEntity, BearerResponse.class).getBody();
        String oAuthToken = getBearerResponse.getAccessToken();
        logger.info("Retrieved bearer token of {}.", oAuthToken);
        this.oAuthToken = oAuthToken;
    }

    private LinkedMultiValueMap<String, String> requestBearerTokenBody() {
        LinkedMultiValueMap<String, String> requestBearerTokenBody = new LinkedMultiValueMap<>();
        requestBearerTokenBody.add("grant_type", "password");
        requestBearerTokenBody.add("username", env.getProperty("admin.user"));
        requestBearerTokenBody.add("password", env.getProperty("admin.password"));
        return requestBearerTokenBody;
    }

    private HttpHeaders requestBearerTokenHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType());
        requestHeaders.add(HttpHeaders.AUTHORIZATION, basicAuthenticationString());
        return requestHeaders;
    }

    private HttpHeaders requestOAuthResourceHeaders() {
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());
        requestHeaders.add(HttpHeaders.AUTHORIZATION, " Bearer " + oAuthToken);
        return requestHeaders;
    }

}
