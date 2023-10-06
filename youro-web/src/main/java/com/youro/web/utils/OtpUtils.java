package com.youro.web.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.util.Random;

@ComponentScan
public class OtpUtils {

    public  String generateOTP(int length) {
        String numbers = "0123456789";
        Random otpGenerator = new Random();
        char[] otp = new char[length];
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt(otpGenerator.nextInt(numbers.length()));
        }
        return new String(otp);
    }
}
