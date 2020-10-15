package com.revature.rms.auth.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cloud.netflix.eureka.EurekaInstanceConfigBean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Component
@Profile("dev")
public class EurekaInstanceConfigBeanPostProcessor implements BeanPostProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(EurekaInstanceConfigBeanPostProcessor.class);

    private String fargateIp;

    {
        try {
            fargateIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            LOGGER.warn("Could not get the Fargate instance ip address.");
        }
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        if (bean instanceof EurekaInstanceConfigBean) {
            LOGGER.info("EurekaInstanceConfigBean detected. Setting IP address to {}", fargateIp);
            EurekaInstanceConfigBean instanceConfigBean = ((EurekaInstanceConfigBean) bean);
            instanceConfigBean.setInstanceId(fargateIp + ":auth-service:10006");
            instanceConfigBean.setIpAddress(fargateIp);
            instanceConfigBean.setStatusPageUrl("http://" + fargateIp + ":10006/actuator/info");
            instanceConfigBean.setHealthCheckUrl("http://" + fargateIp + ":10006/actuator/info");
        }

        return bean;
    }

}
