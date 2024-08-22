package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.logging.Logger;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @RestController
    @CrossOrigin("*")
    static class MainController {
        private final Logger logger = Logger.getLogger(getClass().getName());
        private final ZoneOffset offset = ZoneOffset.of("-03:00");
        @Value("${environment.key}")
        private String CUSTOM_KEY;

        @GetMapping
        public Msg helloFromRender() {
            logger.info(CUSTOM_KEY);
            return new Msg("Hello from render.com", ZonedDateTime.now(offset));
        }

        @GetMapping("{val1}/{val2}")
        public Msg sum(@PathVariable Integer val1, @PathVariable Integer val2) {
            int sum = val1 + val2;
            if (sum > 99)
                return new Msg("You exceed the sum limit (99).", ZonedDateTime.now(offset));
            return new Msg("Total sum is %d".formatted(sum), ZonedDateTime.now(offset));
        }

        @PostMapping
        public Msg saveMsg(@RequestBody(required = false) String msg) {
            if (msg == null)
                return new Msg("Msg cannot be null", ZonedDateTime.now(offset));
            return new Msg("You saved the message { %s }".formatted(msg), ZonedDateTime.now(offset));
        }
    }

    record Msg(String msg, ZonedDateTime time) {
    }

}
