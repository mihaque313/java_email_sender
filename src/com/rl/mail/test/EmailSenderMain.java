package com.rl.mail.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.*;
import java.util.logging.*;

public class EmailSenderMain {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	public static void main(String[] args) throws IOException {
		
		int num_arg=args.length;
		String inputFileType;
		String inputFileName;
		String emailAddresses;
		String subject;
		String fileAttachment;
		String content;
		if(num_arg < 3 || num_arg > 5) {
			System.err.println("Please provide correct no. of args within 3 to 5");
			System.exit(1);
		}
		switch(num_arg) {
			case 5: inputFileType = args[0]; //System.out.println("in html/text with attachment");
					inputFileName = args[1];
					emailAddresses = args[2];
					subject = args[3];
					fileAttachment = args[4];
					content = readFile(inputFileName);
					sendMail(emailAddresses,content,subject,inputFileType,fileAttachment);
					break;
			case 4: if (args[0].equalsIgnoreCase("-html") || args[0].equalsIgnoreCase("-text")){
						inputFileType = args[0]; //System.out.println("in html/text without attachment");
						inputFileName = args[1];
						emailAddresses = args[2];
						subject = args[3];
						fileAttachment = "null";
						content = readFile(inputFileName);
						sendMail(emailAddresses,content,subject,inputFileType,fileAttachment);
					} else {
						inputFileName = args[0]; //System.out.println("in text with attachment");
						emailAddresses = args[1];
						subject = args[2];
						fileAttachment = args[3];
						inputFileType = "text";
						content = readFile(inputFileName);
						sendMail(emailAddresses,content,subject,inputFileType,fileAttachment);
					}
					break;
			case 3: inputFileName = args[0]; //System.out.println("in text without attachment");
					emailAddresses = args[1];
					subject = args[2];
					inputFileType = "text";
					fileAttachment = "null";
					content = readFile(inputFileName);
					sendMail(emailAddresses,content,subject,inputFileType,fileAttachment);
					break;
		}
	}
	
	private static String readFile(String inputFileName) {
		StringBuilder contentBuilder = new StringBuilder();
		try (BufferedReader reader = new BufferedReader(new FileReader(new File(inputFileName)))){
			String line = null;
			while((line = reader.readLine()) != null) {
				contentBuilder.append(line + "\n");
			}
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return contentBuilder.toString();
	}

	public static void sendMail(String to, String content, String subject, String inputFileType, String fileAttachment) throws IOException {
        //Setting up configurations for the email connection to the Google SMTP server using TLS
        Properties props = new Properties();
        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        //Establishing a session with required user details
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("<your_email>", "<password>");
            }
        });
        try {
            //Creating a Message object to set the email content
            MimeMessage msg = new MimeMessage(session);
            //Storing the comma seperated values to email addresses
            /*Parsing the String with defualt delimiter as a comma by marking the boolean as true and storing the email
            addresses in an array of InternetAddress objects*/
            InternetAddress[] address = InternetAddress.parse(to, true);
            //Setting the recepients from the address variable\
            try {
				msg.setFrom(new InternetAddress("<your_email>","<Your_name>"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            msg.setRecipients(Message.RecipientType.TO, address);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            Multipart multipart = new MimeMultipart(); //Making Multipart object

            //making BodyPart object for content.
            BodyPart messageBodyPart = new MimeBodyPart();
            //setContent of html to bodyPart object and then add it to multipart object.
            if(inputFileType.equalsIgnoreCase("-html"))
             	messageBodyPart.setContent(content,"text/html");
            else
            	messageBodyPart.setText(content);
            multipart.addBodyPart(messageBodyPart);
            
            if(!fileAttachment.equalsIgnoreCase("null")) {
            	//making BodyPart object for attachment 		      
 		        
 		        List<String> attachments = Arrays.asList(fileAttachment.split("\\s*,\\s*")); 		        
 		        for(int i = 0; i < attachments.size(); i++){
 		        	LOGGER.log(Level.INFO, "Attaching the file: "+ attachments.get(i));
 		        	MimeBodyPart attachementPart = new MimeBodyPart();
 		        	attachementPart.attachFile(attachments.get(i));
 		        	multipart.addBodyPart(attachementPart);                      
 		        }
            }
            msg.setContent(multipart);
            msg.setHeader("XPriority", "1");
            Transport.send(msg);
            System.out.println("Mail has been sent successfully");
        } catch (MessagingException mex) {
            System.out.println("Unable to send an email" + mex);
        }
    }

}
