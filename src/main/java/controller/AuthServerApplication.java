package controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(scanBasePackages = {"controller", "model", "service", "utils"})
@EnableJpaRepositories(basePackages = {"model.repository"})
@EntityScan(basePackages = {"model"})
@Slf4j
public class AuthServerApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(AuthServerApplication.class);
        Map<String, Object> customConfig = new HashMap<>();
        customConfig.put("server.port", "3003");

        app.setDefaultProperties(customConfig);
        app.run(args);
    }
}
