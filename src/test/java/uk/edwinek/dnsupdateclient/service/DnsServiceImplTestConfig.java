package uk.edwinek.dnsupdateclient.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;
import uk.edwinek.dnsupdateclient.client.DnsUpdateClient;
import uk.edwinek.dnsupdateclient.client.IpifyClient;
import uk.edwinek.dnsupdateclient.client.IpifyClientImpl;

import static org.mockito.Mockito.mock;

@ContextConfiguration
public class DnsServiceImplTestConfig {

    @Bean
    private DnsService dnsService(){
        return new DnsServiceImpl();
    }

    @Bean
    private DnsUpdateClient dnsUpdateClient() {
        return mock(DnsUpdateClient.class);
    }

    @Bean
    private IpifyClient ipifyClient() {
        return mock(IpifyClientImpl.class);
    }

    @Bean
    private RestTemplate restTemplate() {
        return mock(RestTemplate.class);
    }

    @Bean
    private Environment env() {
        return mock(Environment.class);
    }
}
