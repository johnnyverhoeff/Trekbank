/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trekbank;

/**
 *
 * @author Johnny
 */



//import java.util.*;
//import javax.activation.DataHandler;
//import javax.activation.DataSource;
//import javax.activation.FileDataSource;
//import javax.mail.*;
//import javax.mail.internet.*;

public class emailtest {
   //public static void main(String [] args)
   //{    
       //<editor-fold defaultstate="collapsed" desc="comment">
       final String username = "johnnyverhoeff837@gmail.com";
       final String password = "crackhoer911";
       //</editor-fold>

//        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "587");
//
//        Session session = Session.getInstance(props,
//          new javax.mail.Authenticator() {
//                protected PasswordAuthentication getPasswordAuthentication() {
//                        return new PasswordAuthentication(username, password);
//                }
//          });
//
//        try {
//
//                Message message = new MimeMessage(session);
//                message.setFrom(new InternetAddress("JohnnyVerhoeff"));
//                message.setRecipients(Message.RecipientType.TO,
//                        InternetAddress.parse("j.verhoeff@me.com"));
//                message.setSubject("Testing Subject");
//                message.setText("Dear Mail Crawler,"
//                        + "\n\n No spam to my email, please!");
//                
//                // create the message part 
//                MimeBodyPart messageBodyPart = 
//                  new MimeBodyPart();
//
//                //fill message
//                messageBodyPart.setText("Hi");
//
//                Multipart multipart = new MimeMultipart();
//                multipart.addBodyPart(messageBodyPart);
//
//                // Part two is attachment
//                messageBodyPart = new MimeBodyPart();
//                DataSource source = 
//                  new FileDataSource("test.xls");
//                messageBodyPart.setDataHandler(
//                  new DataHandler(source));
//                messageBodyPart.setFileName("test.xls");
//                multipart.addBodyPart(messageBodyPart);
//
//                // Put parts in message
//                message.setContent(multipart);
//
//
//                Transport.send(message);
//
//                System.out.println("Done");
//
//        } catch (MessagingException e) {
//                throw new RuntimeException(e);
//        }
//
   //}
}