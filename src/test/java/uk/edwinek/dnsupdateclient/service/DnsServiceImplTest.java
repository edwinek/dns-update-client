package uk.edwinek.dnsupdateclient.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.edwinek.dnsupdateclient.BaseTest;
import uk.edwinek.dnsupdateclient.client.IpifyClient;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DnsServiceImplTestConfig.class})
public class DnsServiceImplTest extends BaseTest {

    public static final String VALID_IP = "98.207.254.136";
    @Autowired
    private DnsService dnsService;

    @Autowired
    private IpifyClient ipifyClient;

    @Test
    public void updates_old_ip_with_populated_one() {
        String initialIp = "8.8.8.8";
        dnsService.setLastRetrievedIp(initialIp);
        when(ipifyClient.getCurrentPublicIp()).thenReturn(ipifyResponseBody());

        dnsService.checkAndUpdate();

        assertThat("Check that IP has not updated.", dnsService.getLastRetrievedIp(), equalTo(VALID_IP));

        verify(ipifyClient, times(1)).getCurrentPublicIp();
        reset(ipifyClient);
    }

    @Test
    public void updates_null_ip_with_populated_one() {
        String initialIp = null;
        dnsService.setLastRetrievedIp(initialIp);
        when(ipifyClient.getCurrentPublicIp()).thenReturn(ipifyResponseBody());

        dnsService.checkAndUpdate();

        assertThat("Check that IP has not updated.", dnsService.getLastRetrievedIp(), equalTo(VALID_IP));

        verify(ipifyClient, times(1)).getCurrentPublicIp();
        reset(ipifyClient);
    }

    @Test
    public void does_not_update_ip_due_to_malformed_response() {
        String initialIp = "8.8.8.8";
        dnsService.setLastRetrievedIp(initialIp);
        when(ipifyClient.getCurrentPublicIp()).thenReturn(ipifyResponseBodyWithMalformedIp());

        dnsService.checkAndUpdate();

        assertThat("Check that IP has not updated.", dnsService.getLastRetrievedIp(), equalTo(initialIp));

        verify(ipifyClient, times(1)).getCurrentPublicIp();
        reset(ipifyClient);
    }


}
