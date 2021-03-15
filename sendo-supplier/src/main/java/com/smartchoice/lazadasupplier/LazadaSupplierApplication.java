package com.smartchoice.lazadasupplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LazadaSupplierApplication {
    private static Logger logger = LogManager.getLogger(LazadaSupplierApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Lazada supplier service...");
        new SpringApplicationBuilder(LazadaSupplierApplication.class).web(WebApplicationType.NONE).run(args);
    }
}
