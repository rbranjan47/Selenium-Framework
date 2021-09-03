package utils;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
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

import org.apache.commons.io.FileUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import resources.baseTest;

public class utilsTest extends baseTest
{
	public static long timeout = 10;
	public static long pageload = 15;;
	WebDriver driver;

	public utilsTest(WebDriver driver) {
		baseTest.driver = driver;
	}

	public String takescreenshot_driver(String testcasename, WebDriver driver) throws IOException {
		TakesScreenshot screen_shot = (TakesScreenshot) driver;
		File input_source = screen_shot.getScreenshotAs(OutputType.FILE);

		String filePathname = properties.getProperty("user.dir") + "\\reports" + testcasename + ".png";
		File output_source = new File(filePathname);
		// file utils
		FileUtils.copyFile(input_source, output_source);

		return filePathname;
	}

	// emails through COMMONS IO
	public void emails(String Subject) throws EmailException {
		Email email = new SimpleEmail();
		// hostname
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(465);
		// username and password
		String gmail_username = properties.getProperty("from_gmail_email");
		String gmail_password = properties.getProperty("from_gmail_pass");
		email.setAuthenticator(new DefaultAuthenticator(gmail_username, gmail_password));
		email.setSSLOnConnect(true);
		email.setFrom(gmail_username);
		// String mail_subject = properties.getProperty("mail_subject");
		email.setSubject(Subject);
		String gmail_message = properties.getProperty("mail_message");
		email.setMsg(gmail_message);
		String to_gmail_mail = properties.getProperty("to_gmail_email");
		email.addTo(to_gmail_mail);
		email.send();
	}

	// emails through smtp
	public void smtp_emails() throws AddressException, MessagingException {
		Properties proper = System.getProperties();
		// host
		String host = properties.getProperty("smtp_host");
		proper.put("mail.smtp.host", host);
		// port
		String port = properties.getProperty("smtp_port");
		proper.put("mail.smtp.port", port);
		// status
		String status = properties.getProperty("smtp_status");
		proper.put("mail.smtp.ssl.enable", status);
		// authentication status
		String authen_status = properties.getProperty("smtp_status");
		proper.put("mail.smtp.auth", authen_status);

		// getting the session object and pass the username and password
		final String username_smtp = properties.getProperty("from_gmail_email");
		final String password_smtp = properties.getProperty("from_gmail_pass");

		// TLS authentication
		Authenticator auth = new Authenticator() {
			@SuppressWarnings("unused")
			protected PasswordAuthentication getPassworAuthentication() {
				return new PasswordAuthentication(username_smtp, password_smtp);
			}
		};

		// passing the username & password in that session
		Session session = Session.getInstance(proper, auth);
		// debugging
		session.setDebug(true);
		try {
			// creating mimemessage object
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username_smtp));
			String username_to = properties.getProperty("to_gmail_email");
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(username_to));
			String subject_smtp = properties.getProperty("mail_subject");
			message.setSubject(subject_smtp);
			String text = properties.getProperty("mail_message");
			message.setText(text);
			System.out.println("Sending mail...");
			// sending mail
			Transport.send(message);
			System.out.println("Email Sent...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// sending mail with attchements
	public void smtp_attachements_emails(String toEmail, final String username, final String password, String fromEmail,
			String subject, String body) throws AddressException, MessagingException {
		Properties proper = System.getProperties();
		// host
		String host = properties.getProperty("smtp_host");
		proper.put("mail.smtp.host", host);
		// port
		String port = properties.getProperty("smtp_port");
		proper.put("mail.smtp.port", port);
		// status
		String status = properties.getProperty("smtp_status");
		proper.put("mail.smtp.ssl.enable", status);
		// authentication status
		String authen_status = properties.getProperty("smtp_status");
		proper.put("mail.smtp.auth", authen_status);

		// getting the session object and pass the username and password

		// TLS authentication
		Authenticator auth = new Authenticator() {
			@SuppressWarnings("unused")
			protected PasswordAuthentication getPassworAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};

		// passing the username & password in that session

		Session session = Session.getInstance(proper, auth);
		// debugging
		session.setDebug(true);
		try {
			// creating mimemessage object

			MimeMessage message = new MimeMessage(session);
			message.addHeader("Content-type", "text/HTML; charset=UTF-8");
			message.addHeader("format", "flowed");
			message.addHeader("Content-Transfer-Encoding", "8bit");

			message.setFrom(new InternetAddress(fromEmail, "NoReply-JD"));

			message.setReplyTo(InternetAddress.parse(fromEmail, false));

			message.setSubject(subject, "UTF-8");

			message.setSentDate(new Date());

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));

			// Create the message body part
			BodyPart messageBodyPart = new MimeBodyPart();

			// Fill the message
			messageBodyPart.setText(body);

			// Create a multipart message for attachment
			Multipart multipart = new MimeMultipart();

			// Set text message part
			multipart.addBodyPart(messageBodyPart);

			// Second part is attachment
			messageBodyPart = new MimeBodyPart();
			String filename = "abc.txt";
			DataSource source = new FileDataSource(filename);
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
			multipart.addBodyPart(messageBodyPart);

			// Send the complete message parts
			message.setContent(multipart);

			// Send message
			Transport.send(message);
			System.out.println("EMail Sent Successfully with attachment!!");

			String text = properties.getProperty("mail_message");
			message.setText(text);
			System.out.println("Sending mail...");
			// sending mail
			Transport.send(message);
			System.out.println("Email Sent...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
