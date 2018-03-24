package com.ttu.lunchbot.spring.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("lunchbot.facebook.graph")
public class FacebookGraphProperties {

    @Getter
    @Setter
    private String token;
}
