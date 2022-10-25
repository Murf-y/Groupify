package com.murfy.groupify.utils;

import java.util.regex.Pattern;

public class InputValidator {
    public static boolean isEmailValid(String emailAddress) {
        return emailAddress.length() > 0 &&  Pattern.compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
                .matcher(emailAddress)
                .matches();
    }
    public static boolean isPasswordValid(String password){
        return password.length() >= 8;
    }
}
