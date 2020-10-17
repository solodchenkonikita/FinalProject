package com.epam.util;

import com.epam.entity.User;
import org.apache.log4j.Logger;

import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class MailUtils {
    private static final Logger LOG = Logger.getLogger(MailUtils.class);

    private static final Session SESSION = init();
    private static final String theme = "Comment master";

    private static Session init() {
        Session session = null;
        try {
            Context initialContext = new InitialContext();
            session = (Session) initialContext
                    .lookup("java:comp/env/mail/Session");
        } catch (Exception ex) {
            LOG.error("mail session lookup error", ex);
        }
        return session;
    }

    public static void sendEmailAboutComment(User user) {
        try {
            Message msg = new MimeMessage(SESSION);
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(user.getEmail()));

            setContentToComment(msg, user);

            msg.setSentDate(new Date());

            Transport.send(msg);
        } catch (AddressException e) {
            LOG.error(e);
        } catch (MessagingException e) {
            LOG.error(e);
        } catch (UnsupportedEncodingException e) {
            LOG.error(e);
        }
    }

    private static void setContentToComment(Message msg, User user)
            throws MessagingException, UnsupportedEncodingException {
        msg.setSubject(theme);

        Multipart multipart = new MimeMultipart();

        InternetHeaders emailAndPass = new InternetHeaders();
        emailAndPass.addHeader("Content-type", "text/plain; charset=UTF-8");
        String hello = "Hello, " + user.getFirstName() + " " + user.getLastName() + " !\n";

        String data = "We ask you to leave a comment about the quality of service on your user page\n";

        MimeBodyPart greetingAndData = new MimeBodyPart(emailAndPass, (hello + data).getBytes("UTF-8"));

        multipart.addBodyPart(greetingAndData);

        msg.setContent(multipart);
    }
}
