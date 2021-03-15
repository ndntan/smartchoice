package com.smartchoice.sendosupplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SendoSupplierApplication {
    private static Logger logger = LogManager.getLogger(SendoSupplierApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Sendo supplier service...");
        new SpringApplicationBuilder(SendoSupplierApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
