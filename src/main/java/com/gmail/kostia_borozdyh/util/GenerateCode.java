package com.gmail.kostia_borozdyh.util;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class GenerateCode {

    public static String restorePassword(String email) {
        StringBuilder code = generateCode();
        SendEmail.send(email, CreateMessage.restorePassword(code.toString()));
        return code.toString();
    }

    public static String sendCode(String email) {
        StringBuilder code = generateCode();
        SendEmail.send(email, CreateMessage.sendCode(code.toString()));
        return code.toString();
    }

    private static StringBuilder generateCode() {
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(Integer.valueOf((int) (Math.random() * 9)));
        }
        return code;
    }
}
