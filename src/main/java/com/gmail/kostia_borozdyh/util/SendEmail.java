package com.gmail.kostia_borozdyh.util;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendEmail {
    public static void send(String email, String[] message) {
        //Set Mail properties
        Properties props = System.getProperties();
        props.setProperty("mail.smtp.starttls.enable", "true");
        props.setProperty("mail.smtp.host", "smtp.gmail.com");
        props.setProperty("mail.smtp.socketFactory.port", "465");
        props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.setProperty("mail.smtp.auth", "true");
        props.setProperty("mail.smtp.port", "465");
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("manager.delivery.holder@gmail.com", "spayifhyzvspgsjh");
            }
        });

        //Create the email with variable input
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setHeader("Content-Type", "text/plain; charset=UTF-8");
            mimeMessage.setFrom(new InternetAddress("manager.delivery.holder@gmail.com", "manager delivery holder"));
            mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(email));
            mimeMessage.setSubject(message[0], "utf-8");
            mimeMessage.setContent(message[1], "text/plain; charset=UTF-8");
            //Send the email
            Transport.send(mimeMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // If API for sending email has been broken use this code
       /* System.out.println("Email to: "+email);
        System.out.println("Subject: "+message[0]);
        System.out.println("Content: "+message[1]);*/
    }
}
