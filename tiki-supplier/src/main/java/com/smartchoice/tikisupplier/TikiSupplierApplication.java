package com.smartchoice.tikisupplier;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TikiSupplierApplication {

    private static Logger logger = LogManager.getLogger(TikiSupplierApplication.class);

    public static void main(String[] args) {
        logger.info("Starting Lazada supplier service...");
        SpringApplication.run(TikiSupplierApplication.class, args);
    }

}
