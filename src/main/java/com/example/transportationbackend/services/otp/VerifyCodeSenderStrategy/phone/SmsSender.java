package com.example.transportationbackend.services.otp.VerifyCodeSenderStrategy.phone;

import com.example.transportationbackend.services.otp.OTPService;
import com.example.transportationbackend.services.otp.SmsSenderRequestModel;
import com.example.transportationbackend.services.otp.VerifyCodeSenderStrategy.CodeSender;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

public class SmsSender implements CodeSender {

    private final OTPService otpService;
    private SmsSenderRequestModel smsRequestModel;
    private String otpId;

    public SmsSender(OTPService otpService,
                     String id,
                     SmsSenderRequestModel smsModel) {
        this.otpService = otpService;
        this.otpId = id;
        this.smsRequestModel = smsModel;
    }

    @Override
    public void sendSms() {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(smsRequestModel.toString(),
                headers);
//        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:3001/transportation/sms",
//                entity,
//                String.class);
//
//        Assert.isTrue(response.getStatusCodeValue() == 200,
//                response.getBody());
        System.out.println(otpService.getOtp(otpId));
    }
}
