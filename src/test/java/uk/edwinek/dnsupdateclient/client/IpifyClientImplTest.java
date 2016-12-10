package uk.edwinek.dnsupdateclient.client;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import uk.edwinek.dnsupdateclient.BaseTest;
import uk.edwinek.dnsupdateclient.model.IpifyResponse;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {IpifyClientImplTestConfig.class})
public class IpifyClientImplTest extends BaseTest {

    private static final String EXPECTED_IP = "98.207.254.136";
    private static final String DUMMY_URL = "http://www.example.com/";
    private static final String URL_PROPERTY = "url.ipify";

    @Autowired
    private IpifyClient ipifyClient;

    @Autowired
    private Environment env;

    @Autowired
    private RestTemplate restTemplate;

    @Before
    public void setup() {
        when(env.getProperty(URL_PROPERTY)).thenReturn(DUMMY_URL);
        when(restTemplate.getForEntity(DUMMY_URL, IpifyResponse.class)).thenReturn(validIpifyResponse());
    }

    @Test
    public void happyPath() {
        IpifyResponse currentPublicIpResponse = ipifyClient.getCurrentPublicIp();
        assertThat("Check expected IP returned.", currentPublicIpResponse.getIp(), equalTo(EXPECTED_IP));
        verify(restTemplate, times(1)).getForEntity(DUMMY_URL, IpifyResponse.class);
        verify(env, times(1)).getProperty(URL_PROPERTY);
        reset(env);
        reset(restTemplate);
    }




}
