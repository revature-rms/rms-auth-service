package com.revature.rms.auth;

import com.revature.rms.core.config.EurekaInstanceConfigBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@EnableEurekaClient
@SpringBootApplication
public class AuthServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

    @Bean
    public EurekaInstanceConfigBeanPostProcessor eurekaInstanceConfigBeanPostProcessor(){
        return new EurekaInstanceConfigBeanPostProcessor();
    }

}
