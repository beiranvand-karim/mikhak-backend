package com.example.transportationbackend.util;

import com.example.transportationbackend.services.otp.SmsSenderRequestModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
@ComponentScan
public class CodeSenderConfiguration {

    private final Environment env;

    public CodeSenderConfiguration(Environment env) {
        this.env = env;
    }

    @Bean
    public SmsSenderRequestModel createSmsModel(){
        SmsSenderRequestModel smsModel = new SmsSenderRequestModel(env.getProperty("sms.username"),
                env.getProperty("sms.password"),
                env.getProperty("sms.from"),
                env.getProperty("sms.url"));
        return smsModel;
    }
}
