package com.ch13emaillist.util;

import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;

public class MailUtilGmail {

    private static final String GMAIL_USERNAME =
            "ngtduy4240@gmail.com";

    private static final String GMAIL_PASSWORD =
            "xwja dxpw ejwi ebuw";

    public static void sendMail(String to, String from,
                                String subject, String body,
                                boolean bodyIsHTML)
            throws MessagingException {

        // 1 - Configure Gmail SMTP over SSL
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtps");
        props.setProperty("mail.smtps.host", "smtp.gmail.com");
        props.setProperty("mail.smtps.port", "465");
        props.setProperty("mail.smtps.auth", "true");
        props.setProperty("mail.smtps.ssl.checkserveridentity", "true");
        props.setProperty("mail.smtps.connectiontimeout", "10000");
        props.setProperty("mail.smtps.timeout", "10000");
        props.setProperty("mail.smtps.writetimeout", "10000");

        Session session = Session.getInstance(props);

        // 2 - Validate the sender
        if (from == null || from.isBlank()) {
            from = GMAIL_USERNAME;
        }

        if (!GMAIL_USERNAME.equalsIgnoreCase(from.trim())) {
            throw new MessagingException(
                    "The sender address must match GMAIL_USERNAME.");
        }

        // 3 - Create the message with UTF-8
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(GMAIL_USERNAME));
        message.setRecipient(
                Message.RecipientType.TO,
                new InternetAddress(to)
        );

        message.setSubject(subject, "UTF-8");
        message.setText(
                body,
                "UTF-8",
                bodyIsHTML ? "html" : "plain"
        );
        message.saveChanges();

        // 4 - Send and close the connection automatically
        try (Transport transport = session.getTransport("smtps")) {
            transport.connect(
                    "smtp.gmail.com",
                    GMAIL_USERNAME,
                    GMAIL_PASSWORD.replace(" ", "")
            );

            transport.sendMessage(
                    message,
                    message.getAllRecipients()
            );
        }
    }
}