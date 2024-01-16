/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cbt_ca.reusable_functions;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.mail.PasswordAuthentication;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author HP PROBOOK 430 G3
 */
public class mailSender {
    public void sendMail(String firstName,String lastName, String email, String testName, LocalDate testDate, Integer hours, Integer minutes,  Integer numberOfQuesions, String courseName, String courseCode, String level, String department, LocalTime localTime){
        String senderEmail = "nnadiukwumiracleike@gmail.com";
        String senderPassword = "leblkiyatzrvpwby";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
       


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
        });
        
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("CBT Test schedule for " + courseCode);
            
            
            String htmlContent = "<html><body>"
                + "<h3>Hello " + firstName + " " + lastName + ",</h3>" 
                + "<div style = \"width: 100%;\">"
                    + "<p style = \"width: 100%; text-align:center\">A test has been scheduled for " + courseCode + ". Please find the attached details: </p>"
                    + "<p style = \"width: 100%; text-align:center\">Course: " + courseCode + " - " + courseName + "</p>"
                    + "<p style = \"width: 100%; text-align:center\">Department: " + department + " - " + level + "</p>"
                    + "<p style = \"width: 100%; text-align:center\">Test: " + testName + "</p>"
                    + "<p style = \"width: 100%; text-align:center\">Date: " + testDate + ", Time: " + localTime + "</p>"
                    + "<p style = \"width: 100%; text-align:center\">Duration: " + hours + " hours " + minutes + " minutes</p>"
                    + "<p style = \"width: 100%; text-align:center\">Number of questions: " + numberOfQuesions + "</p>"
                + "</div>"
                + "<p style = \"width: 100%; text-align: center; font-size: 12px\">If you are not the intended recipient, please ignore this mail</p>"
            + "</body></html>";
            
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, "text/html");
            
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Set the message content
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Mail sent");
            
        }catch(MessagingException e){
            System.out.println(e);
        }
        
    }
    
    public void sendMailToLecturer(String firstName, String email, String staffID, String password){
        String senderEmail = "nnadiukwumiracleike@gmail.com";
        String senderPassword = "leblkiyatzrvpwby";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
       


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
        });
        
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("CBT Student registration");
            
            
            String htmlContent = "<html><body>"
                + "<h3>Hello " + firstName + ",</h3>" 
                + "<div style = \"width: 100%;\">"
                    + "<p style = \"width: 100%; text-align:center\">Your registration process is complete, please find the attached details: </p>"
                    + "<p style = \"width: 100%; text-align:center\">Staff ID: " + staffID + "</p>"
                    + "<p style = \"width: 100%; text-align:center\">Access password: " + password + "</p>"
                    + "<hr>"
                    + "<p style = \"width: 100%; text-align:center; color: red\">Please do not share this detail with anyone</p>"
                + "</div>"
                + "<p style = \"width: 100%; text-align: center; font-size: 12px\">If you are not the intended recipient, please ignore this mail</p>"
            + "</body></html>";
            
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, "text/html");
            
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Set the message content
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Mail sent");
            
        }catch(MessagingException e){
            System.out.println(e);
        }
        
    }
    
    public void sendMail(String firstName,  String email, String password){
        String senderEmail = "nnadiukwumiracleike@gmail.com";
        String senderPassword = "leblkiyatzrvpwby";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
       


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
        });
        
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("CBT Student registration");
            
            
            String htmlContent = "<html><body>"
                + "<h3>Hello " + firstName + ",</h3>" 
                + "<div style = \"width: 100%;\">"
                    + "<p style = \"width: 100%; text-align:center\">Your registration process is complete, please find the attached details: </p>"
                    + "<p style = \"width: 100%; text-align:center\">Access password: " + password + "</p>"
                    + "<hr>"
                    + "<p style = \"width: 100%; text-align:center; color: red\">Please do not share this detail with anyone</p>"
                + "</div>"
                + "<p style = \"width: 100%; text-align: center; font-size: 12px\">If you are not the intended recipient, please ignore this mail</p>"
            + "</body></html>";
            
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, "text/html");
            
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Set the message content
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Mail sent");
            
        }catch(MessagingException e){
            System.out.println(e);
        }
        
    }
    
    public void resetPasswordMail(String firstName,  String email, String password){
        String senderEmail = "nnadiukwumiracleike@gmail.com";
        String senderPassword = "leblkiyatzrvpwby";
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");
       


        Session session = Session.getInstance(props,
                new javax.mail.Authenticator(){
                @Override
                protected PasswordAuthentication getPasswordAuthentication(){
                    return new PasswordAuthentication(senderEmail, senderPassword);
                }
        });
        
        try{
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(senderEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Password reset");
            
            
            String htmlContent = "<html><body>"
                + "<h3>Hello " + firstName + ",</h3>" 
                + "<div style = \"width: 100%;\">"
                    + "<p style = \"width: 100%; text-align:center\">You requested a password reset, please find the attached details: </p>"
                    + "<p style = \"width: 100%; text-align:center\">New access password: " + password + "</p>"
                    + "<hr>"
                    + "<p style = \"width: 100%; text-align:center; color: red\">Please do not share this detail with anyone</p>"
                + "</div>"
                + "<p style = \"width: 100%; text-align: center; font-size: 12px\">If you are not the intended recipient, please ignore this mail</p>"
            + "</body></html>";
            
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(htmlContent, "text/html");
            
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);

            // Set the message content
            message.setContent(multipart);
            Transport.send(message);
            System.out.println("Mail sent");
            
        }catch(MessagingException e){
            System.out.println(e);
        }
        
    }
}
