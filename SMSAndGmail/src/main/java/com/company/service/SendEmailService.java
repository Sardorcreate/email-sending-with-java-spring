package com.company.service;

import jakarta.mail.*;
import jakarta.mail.Flags.Flag;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class SendEmailService {

    @Value("${mail}")
    private static String myEmail;

    public static void sendMail(String messagee, String randomCode) throws Exception {

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        String myAccountEmail = "if.magiic3105@gmail.com";

        String myPassword = "lwsz swrk ljuf fwva";

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myEmail, myPassword);
            }
        });

        Message message = prepareMessage(session, myAccountEmail, messagee, randomCode);

        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient, String randomCode) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("LMS tizimi");
            message.setText("Tasdiqlash kodi: " + randomCode);

            return message;
        } catch (AddressException e) {
            throw new RuntimeException(e);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

}
