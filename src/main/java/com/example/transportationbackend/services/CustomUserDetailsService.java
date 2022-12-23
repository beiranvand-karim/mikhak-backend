package com.example.transportationbackend.services;

import com.example.transportationbackend.auth.CustomUserDetails;
import com.example.transportationbackend.models.UserModel;
import com.example.transportationbackend.models.enums.UserVerificationState;
import com.example.transportationbackend.repositories.UserRepository;
import com.example.transportationbackend.services.otp.OTPService;
import com.example.transportationbackend.services.otp.SmsSenderRequestModel;
import com.example.transportationbackend.services.otp.VerifyCodeSenderStrategy.CodeSender;
import com.example.transportationbackend.services.otp.VerifyCodeSenderStrategy.phone.SmsSender;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@ComponentScan
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private OTPService otpService;

    @Autowired
    private SmsSenderRequestModel smsModel;

    private CodeSender codeSender;

    private UserModel savedUser;

    @Override
    public UserDetails loadUserByUsername(String id)
            throws UsernameNotFoundException {
        UserModel user = null;
        if (userRepository.existsUserModelByIdOrEmailAddressOrPhone(id, id, id))
            user = userRepository.findUserModelByIdOrEmailAddressOrPhone(id, id, id);
        else {
            throw new UsernameNotFoundException("Could not find id ");
        }
        return new CustomUserDetails(user);
    }

    public UserVerificationState checkUser(String id) throws Exception {
        UserVerificationState state;
        if (userRepository.existsUserModelByIdOrEmailAddressOrPhone(id, id, id)) {
            savedUser = userRepository.findUserModelByIdOrEmailAddressOrPhone(id, id, id);
            if (savedUser.getPassword() == null ||
                    savedUser.getPassword().isEmpty()) {
                state = UserVerificationState.First_Login;
                setUpOTP(id);
                setUpCodeSender(id);
                sendCode();
            } else {
                state = UserVerificationState.User_Checked;
            }
        } else
            state = UserVerificationState.Access_Denied;
        return state;
    }

    private void setUpCodeSender(String id) {
        try {
            if (StringUtils.isNumeric(id)
                    && id.startsWith("0")) {
                int otp = otpService.getOtp(id);
                smsModel.setOtp(otp);
                smsModel.setReceiverMessageInfo(id);
                codeSender = new SmsSender(otpService,
                        id,
                        smsModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpOTP(String id) {
        try {
            otpService.generateOtp(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendCode() {
        codeSender.sendSms();
    }

    public UserVerificationState verifyUserByConfirmationCode(int confirmCode,
                                                              String password,
                                                              String id) throws Exception {
        UserVerificationState state;
        if (otpService.getOtp(id) == confirmCode) {
            String passwordEncoded = passwordEncoder.encode(password);
            state = UserVerificationState.User_Verified;
            savedUser.setPassword(passwordEncoded);
            userRepository.save(savedUser);
        } else {
            state = UserVerificationState.Access_Denied;
        }
        return state;
    }
}
