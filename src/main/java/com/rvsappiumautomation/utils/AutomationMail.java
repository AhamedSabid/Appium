package com.rvsappiumautomation.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.rvsappiumautomation.data.PropertiesDataHandler;

public class AutomationMail {
	
	/**
	 * Function to get send mail to recipients with attached automation report and device log
	 * @author rahul.raman
	 * @throws AddressException
	 * @throws MessagingException
	 * @throws IOException
	 */
	public static void sendMailReport() throws AddressException, MessagingException, IOException {
		String subject = "", message = "", maillist = "", EMAIL_REPORT_FOLDER, DEVICE_LOG_FOLDER = "";
		
		subject = PropertiesDataHandler.getProperty(AutomationConstants.EMAIL_CONFIG, "subject");
		message = PropertiesDataHandler.getProperty(AutomationConstants.EMAIL_CONFIG, "message");
		maillist = PropertiesDataHandler.getProperty(AutomationConstants.EMAIL_CONFIG, "maillist");
		EMAIL_REPORT_FOLDER = PropertiesDataHandler.getProperty(AutomationConstants.EMAIL_CONFIG, "EMAIL_REPORT_FOLDER");
		DEVICE_LOG_FOLDER=PropertiesDataHandler.getProperty(AutomationConstants.EMAIL_CONFIG, "DEVICE_LOG_FOLDER");
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		final String userName;
		final String password;
		userName = "rvsautomationtester@gmail.com";
		password = "Automation@123";
		properties.put("mail.user", userName);
		properties.put("mail.password", password);

		Authenticator auth = new Authenticator() {
			public PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(userName, password);
			}
		};
		Session session = Session.getInstance(properties, auth);
		DateFormat dff = new SimpleDateFormat("EEE MMM dd, yyyy HH:mm:ss z");
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(userName));
		msg.addRecipients(Message.RecipientType.TO, InternetAddress.parse(maillist));
		msg.setSubject(subject + " – " + dff.format(new Date()).toString());
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(message, "text/html");

		// creates multi-part
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);

		// adds all files in attachments
		/*List<String> filesList = new ArrayList<String>();
		File[] files = new File(System.getProperty("user.dir") + EMAIL_REPORT_FOLDER).listFiles();
		for (File file : files) {
			if (file.isFile()) {
				filesList.add(System.getProperty("user.dir") + EMAIL_REPORT_FOLDER + "/" + file.getName());
			}
		}
		if (filesList != null && filesList.size() > 0) {
			for (String filePath : filesList) {
				MimeBodyPart attachPart = new MimeBodyPart();
				try {
					attachPart.attachFile(filePath);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				multipart.addBodyPart(attachPart);
			}
		}*/
		
		// adds only the latest Report file in attachments
		MimeBodyPart attachPart = new MimeBodyPart();
		attachPart.attachFile(lastFileModified(System.getProperty("user.dir") + EMAIL_REPORT_FOLDER));
		multipart.addBodyPart(attachPart);
		
		// adds only the latest Log file in attachments
		MimeBodyPart attachLogPart = new MimeBodyPart();
		attachLogPart.attachFile(lastFileModified(System.getProperty("user.dir") + DEVICE_LOG_FOLDER));
		multipart.addBodyPart(attachLogPart);
		
		
		
		// sets the multi-part as e-mail’s content
		msg.setContent(multipart);
		// sends the e-mail
		Transport.send(msg);
	}

	public static File lastFileModified(String dir) {
		File fl = new File(dir);
		File[] files = fl.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastMod = Long.MIN_VALUE;
		File choice = null;
		for (File file : files) {
			if (file.lastModified() > lastMod) {
				choice = file;
				lastMod = file.lastModified();
			}
		}
		return choice;
	}
}
