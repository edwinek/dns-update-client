package uk.edwinek.dnsupdateclient.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uk.edwinek.dnsupdateclient.model.IpifyResponse;

@Component
public class IpifyClientImpl implements IpifyClient {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public IpifyResponse getCurrentPublicIp() {
        String url = env.getProperty("url.ipify");
        logger.info("Retrieving current public ip for host.");
        ResponseEntity<IpifyResponse> ipifyResponse = restTemplate.getForEntity(url, IpifyResponse.class);
        return ipifyResponse.getBody();
    }

}
