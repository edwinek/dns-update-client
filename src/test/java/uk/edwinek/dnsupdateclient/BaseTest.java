package uk.edwinek.dnsupdateclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import uk.edwinek.dnsupdateclient.model.IpifyResponse;

import java.io.IOException;

public abstract class BaseTest {

    private ObjectMapper mapper = new ObjectMapper();
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected IpifyResponse ipifyReponseJson(String resourceName) {
        try {
            return mapper.readValue(testResourceAsString(resourceName), IpifyResponse.class);
        } catch (IOException e) {
            logger.error("Unable to marshal the specified file \"{}\".", resourceName);
            throw new RuntimeException("Unable to marshal the specified file \"" + resourceName + "\".");
        }
    }

    protected String testResourceAsString(String fileName) {
        try {
            return IOUtils.toString(getClass().getClassLoader().getResourceAsStream(fileName), "UTF-8");
        } catch (IOException e) {
            logger.error("Unable to open the specified file \"{}\".", fileName);
            throw new RuntimeException("Unable to open the specified file \"" + fileName + "\".");
        }
    }
    protected ResponseEntity<IpifyResponse> validIpifyResponseWithMalformedIP() {
        return new ResponseEntity<>(ipifyResponseBody(), HttpStatus.OK);
    }

    protected ResponseEntity<IpifyResponse> validIpifyResponse() {
        return new ResponseEntity<>(ipifyResponseBody(), HttpStatus.OK);
    }

    protected IpifyResponse ipifyResponseBody() {
        return ipifyReponseJson("ValidResponse.json");
    }

    protected IpifyResponse ipifyResponseBodyWithMalformedIp() {
        return ipifyReponseJson("ResponseWithMalformedIP.json");
    }

}
