package com.ttu.lunchbot.spring;

import com.ttu.lunchbot.spring.property.FacebookGraphProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages={"com.ttu.lunchbot.spring"})
@EnableJpaRepositories("com.ttu.lunchbot.spring.repository")
@EnableConfigurationProperties
public class LunchbotApplication {

	public static void main	(String[] args) {
		SpringApplication.run(LunchbotApplication.class, args);
	}

}
