package uk.edwinek.dnsupdateclient.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.mock;

@ContextConfiguration
@ComponentScan({"uk.edwinek.dnsupdateclient.client"})
public class IpifyClientImplTestConfig {

    @Bean
    private Environment env() {
        return mock(Environment.class);
    }

    @Bean
    private RestTemplate restTemplate() {
        return mock(RestTemplate.class);
    }

}
