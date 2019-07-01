package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import lombok.Data;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MailController {
    
    private static final String TO               = "contact@kelrisks.beta.gouv.fr";
    private static final String FROM             = "kelrisks.betagouv@gmail.com";
    private static final String FROM_FULL        = "kelrisks.beta.gouv.fr";
    private static final String SUBJECT          = "[Kelrisks] Un commentaire, une remarque, un problème?";
    private static final String SMTP_SERVER_HOST = "smtp.gmail.com";
    private static final String SMTP_SERVER_PORT = "587";
    
    @Autowired
    JavaMailSender javaMailSender;
    
    @RequestMapping("/mail")
    @ResponseBody
    public Response sendMail(@RequestBody() MailModel mailModel) {
        
        String body = "Bonjour.<br>" +
                      "Le message suivant a été envoyé par un utilisateur via Kelrisks :<br><br>" +
                      "<i>" + mailModel.getContent() + "</i><br><br>" +
                      "Kelrisks";
        
        sendMail(body);
        
        return Response.ok().build();
    }
    
    private void sendMail(String body) {
        
        try {
            Properties props = System.getProperties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.host", "smtp.gmail.com");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.debug", "true");
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.socketFactory.fallback", "false");
            Session session = Session.getDefaultInstance(props, new Authenticator() {
                
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    
                    return new PasswordAuthentication("kelrisks.betagouv@gmail.com", "EssonneP");
                }
            });
            session.setDebug(true);
            
            MimeMessage     mail        = new MimeMessage(session);
            InternetAddress addressFrom = new InternetAddress(FROM, FROM_FULL);
            mail.setSender(addressFrom);
            mail.setFrom(addressFrom);
            mail.setRecipient(Message.RecipientType.TO, new InternetAddress(TO));
            mail.setSubject(SUBJECT + " (Ref. " + (int) Math.floor(Math.random() * 8999 + 1000) + ")");
            mail.setContent(body, "text/html");
            
            Transport transport = session.getTransport();
            transport.connect();
            //            transport.issueCommand("AUTH XOAUTH2 " + new String(BASE64EncoderStream.encode(String.format("user=%s\1auth=Bearer %s\1\1", smtpUserName, smtpUserAccessToken).getBytes()))
            //            , 235);
            Transport.send(mail);
            transport.close();
        }
        catch (Exception ex) {
            //            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    
    @Data
    public static class MailModel {
        
        String content;
    }
}

