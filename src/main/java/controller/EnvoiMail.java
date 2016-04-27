/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.Utilisateur;

/**
 *
 * @author banana
 */
public class EnvoiMail {

    static String sendEmail = "multimilf.groupek@gmail.com";
    static String sendMdp = "Multimif";
    static String sendServeur = "smtp.gmail.com";

    static Properties mailServerProperties;
    static Session getMailSession;
    static MimeMessage generateMailMessage;

    public static void invitUsers(String confName, String confId, ArrayList<String> listContact) {

        try {
            initMsg();

            String link = "http://localhost:8084/multimif/inscription.htm?idConf=" + confId;

            String subject = "Créer votre compte Sympozer Ze Backend";
            String emailBody = ""
                    + "<b>Bonjour</b>, <br>"
                    + "<p>Vous avez été ajouté en tant que chair à la conférence <b>" + confName + "</b>. </p>"
                    + "<p>Pour créer votre compte Sympozer et rejoindre automatiquement cette conférence,<br>"
                    + "cliquer sur le lien suivant : </p>"
                    + "<a href=\"" + link + "\">" + link + "</a>"
                    + "<p>À bientôt sur Sympozer Ze Backend !</p>"
                    + "<span style=\"color:red;\">L'équie K.</span>";

            for (String mail : listContact) {
                if (mail != null) {
                    generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
//                generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("anna.benneton@gmail.com"));
                }
            }

            generateMailMessage.setSubject(subject);
            generateMailMessage.setContent(emailBody, "text/html; charset=UTF-8");
            System.out.println("Mail Session has been created successfully..");

            sendMsg();
        } catch (MessagingException ex) {
            Logger.getLogger(EnvoiMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void oublieMdp() {

    }

    public static void confirmAccount(Utilisateur user) {

        try {
            initMsg();

            String link = "http://localhost:8084/multimif/";

            String subject = "Bienvenue sur Sympozer Ze Backend ";
            String emailBody = ""
                    + "<b>Bonjour</b>, <br>"
                    + "<p>Vous venez de créer un compte sur Sympozer !</p>"
                    + "Rappel de vos identifiant:"
                    + "<p style=\"margin-left:40px\">"
                    + "<b>mail: </b> " + user.getMail()
                    + "<br><b>pseudo: </b> " + user.getPseudo()
                    + "</p>"
                    + "<p>Pour accéder au site, vous pouvez cliquer sur le lien suivant : </p>"
                    + "<a href=\"" + link + "\">" + link + "</a>"
                    + "<p>À bientôt sur Sympozer Ze Backend !</p>"
                    + "<span style=\"color:red;\">L'équie K.</span>";

            if (user.getMail() != null) {
                generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(user.getMail()));
//                generateMailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress("anna.benneton@gmail.com"));
            }

            generateMailMessage.setSubject(subject);
            generateMailMessage.setContent(emailBody, "text/html; charset=UTF-8");
            System.out.println("Mail Session has been created successfully..");

            sendMsg();
        } catch (MessagingException ex) {
            Logger.getLogger(EnvoiMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void joinConf(String confName, ArrayList<String> listContact) {
        try {
            initMsg();

            String subject = "Nouvelle conférence sur Sympozer Ze Backend .";
            String emailBody = ""
                    + "<b>Bonjour</b>, <br>"
                    + "<p>Vous avez été ajouté en tant que chair à la conférence " + confName + ". </p>"
                    + "<p>À bientôt sur Sympozer Ze Backend !</p>"
                    + "<span style=\"color:red;\">L'équie K.</span>";

            for (String mail : listContact) {
                if (mail != null) {
//                    generateMailMessage.setRecipients(Message.RecipientType.TO,
//                            InternetAddress.parse(mail));
                    generateMailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(mail));
                }
            }

            generateMailMessage.setSubject(subject);
            generateMailMessage.setContent(emailBody, "text/html; charset=UTF-8");
            System.out.println("Mail Session has been created successfully..");

            sendMsg();
        } catch (MessagingException ex) {
            Logger.getLogger(EnvoiMail.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void initMsg() throws AddressException, MessagingException {
        // Step1
        System.out.println("\n 1st ===> setup Mail Server Properties..");

        mailServerProperties = System.getProperties();
        mailServerProperties.put("mail.smtp.port", "587");
        mailServerProperties.put("mail.smtp.auth", "true");
        mailServerProperties.put("mail.smtp.starttls.enable", "true");
        mailServerProperties.put("mail.smtp.host", "smtp.gmail.com");

        getMailSession = Session.getInstance(mailServerProperties,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(sendEmail, sendMdp);
                }
            });

/*        mailServerProperties.put("mail.smtp.ssl.checkserveridentity", "false");
        mailServerProperties.put("mail.smtp.ssl.trust", "*");
        */
        System.out.println("Mail Server Properties have been setup successfully..");
        // Step2
        System.out.println("\n\n 2nd ===> get Mail Session..");

//        getMailSession = Session.getDefaultInstance(mailServerProperties, null);
        generateMailMessage = new MimeMessage(getMailSession);
        generateMailMessage.setFrom(new InternetAddress(sendEmail));
    }

    private static void sendMsg() throws AddressException, MessagingException {
        // Step3
        System.out.println("\n\n 3rd ===> Get Session and Send mail");
//        Transport transport = getMailSession.getTransport("smtp");

        // Enter your correct gmail UserID and Password
        // if you have 2FA enabled then provide App Specific Password
//        transport.connect(sendServeur, sendEmail, sendMdp);
//        transport.sendMessage(generateMailMessage, generateMailMessage.getAllRecipients());
//        transport.close();
        Transport.send(generateMailMessage);
    }
}
