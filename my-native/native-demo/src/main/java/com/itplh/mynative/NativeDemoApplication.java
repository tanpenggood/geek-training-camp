package com.itplh.mynative;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.nativex.hint.NativeHint;
import org.springframework.nativex.hint.ResourceHint;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStreamReader;

@NativeHint(
        resources = @ResourceHint(patterns = "hello.txt")
)
@RestController
@SpringBootApplication
public class NativeDemoApplication {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(NativeDemoApplication.class, args);
    }

    @GetMapping("/helloworld")
    public String helloworld() {
        logger.info("Hello, Spring Native.");
        return "Hello, Spring Native.";
    }

    @Bean
    CommandLineRunner resourceRunner(@Value("classpath:hello.txt") Resource resource) {
        return args -> {
            System.out.println("==============================resourceRunner");
            try (InputStreamReader in = new InputStreamReader(resource.getInputStream())) {
                String contents = FileCopyUtils.copyToString(in);
                for (String line : contents.split("\r")) {
                    System.out.println(line);
                }
            }
        };
    }

}
