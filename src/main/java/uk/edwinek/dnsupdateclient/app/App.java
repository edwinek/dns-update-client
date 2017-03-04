package uk.edwinek.dnsupdateclient.app;

import it.sauronsoftware.cron4j.Scheduler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import uk.edwinek.dnsupdateclient.config.ApplicationConfig;
import uk.edwinek.dnsupdateclient.service.DnsService;
import uk.edwinek.dnsupdateclient.service.DnsServiceImpl;

public class App {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        DnsService dnsService = applicationContext.getBean(DnsServiceImpl.class);
        Scheduler scheduler = new Scheduler();
        scheduler.schedule("*/5 * * * *", dnsService::checkAndUpdate);
        scheduler.start();
    }

}
