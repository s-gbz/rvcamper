package de.grilborzer.rvcamper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestRvcamperApplication {

    public static void main(String[] args) {
        SpringApplication.from(RvcamperApplication::main).with(TestRvcamperApplication.class).run(args);
    }

}
