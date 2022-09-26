package com.verasretail.support;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.apache.commons.lang3.StringUtils;

public class ReportMail
{
	private static final String MAIL_SMTP_HOST = "mail.smtp.host";
	private static final String MAIL_SMTP_PORT = "mail.smtp.port";
	private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
	private static final String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	private static final String MAIL_SMTPS_SSL_TRUST = "mail.smtps.ssl.trust";
	private static final String MAIL_SMTP_AUTH_FLAG = "true";
	public static final String SEND_MAIL_RECIPIENTS = "send.mail.recipients";
	public static final String SEND_MAIL_HOST_SERVER = "send.mail.host.server";
	public static final String SEND_MAIL_HOST_PORT = "send.mail.host.port";
	public static final String SEND_MAIL_FROM = "send.mail.from";
	public static final String SEND_MAIL_PASSWORD = "send.mail.password";
	public static final String SEND_MAIL_SSL_ENABLED = "send.mail.ssl.enabled";
	public static final String SEND_MAIL_AUTHENTICATION_ENABLED = "send.mail.authentication.enabled";
	public static final String SEND_MAIL_ALIAS_FROM_ADDRESS = "send.mail.alias.from.address";
	public static final String SEND_MAIL_ENABLED = "send.mail.enabled";

	public static void sendMail(Properties props, String subject, String message, String filePath, String fileName, String reportFolderPath)
	{
		try
		{
			if (Boolean.parseBoolean(props.getProperty(SEND_MAIL_ENABLED)))
			{

				if (props.getProperty(SEND_MAIL_HOST_SERVER).contains("?"))
				{
					// log.error("Email SMTP host server name not configured. Please configure
					// 'EmailSmtpHost' preference and rerun.");
				}
				else
				{
					String[] recipients = props.getProperty(SEND_MAIL_RECIPIENTS).split(",");
					// Set the host SMTP address
					Properties mailProps = new Properties();
					mailProps.put(MAIL_SMTP_HOST, props.getProperty(SEND_MAIL_HOST_SERVER));
					mailProps.put(MAIL_SMTP_PORT, props.getProperty(SEND_MAIL_HOST_PORT));

					Session session;
					if (Boolean.parseBoolean(props.getProperty(SEND_MAIL_AUTHENTICATION_ENABLED)))
					{
						if (props.getProperty(SEND_MAIL_PASSWORD) != null)
						{
							mailProps.put(MAIL_SMTP_AUTH, MAIL_SMTP_AUTH_FLAG);
							mailProps.put(MAIL_SMTP_STARTTLS_ENABLE, props.getProperty(SEND_MAIL_SSL_ENABLED));
							mailProps.put(MAIL_SMTPS_SSL_TRUST, props.getProperty(SEND_MAIL_HOST_SERVER));

							session = Session.getInstance(mailProps, new Authenticator()
								{
									@Override
									protected PasswordAuthentication getPasswordAuthentication()
									{
										return new PasswordAuthentication(props.getProperty(SEND_MAIL_FROM),
												props.getProperty(SEND_MAIL_PASSWORD));
									}
								});
						}
						else
						{
							session = Session.getInstance(mailProps, null);
						}
					}
					else
					{
						session = Session.getInstance(mailProps, null);
					}

					// create a message
					Message msg = new MimeMessage(session);

					// set the from and to address
					InternetAddress addressFrom = null;
					if (StringUtils.isNotEmpty(props.getProperty(SEND_MAIL_ALIAS_FROM_ADDRESS)))
					{
						addressFrom = new InternetAddress(props.getProperty(SEND_MAIL_FROM),
								props.getProperty(SEND_MAIL_ALIAS_FROM_ADDRESS));
					}
					else
					{
						addressFrom = new InternetAddress(props.getProperty(SEND_MAIL_FROM));
					}
					msg.setFrom(addressFrom);

					InternetAddress[] addressTo = new InternetAddress[recipients.length];
					for (int i = 0; i < recipients.length; i++)
					{
						addressTo[i] = new InternetAddress(recipients[i]);
					}
					msg.setRecipients(Message.RecipientType.TO, addressTo);

					// Setting the Subject and Content Type
					msg.setSubject(subject);

					MimeBodyPart body = new MimeBodyPart();
					if (message.contains("<!DOCTYPE html>"))
					{
						body.setContent(message, "text/html");
					}
					else
					{
						body.setContent(message, "text/plain");
					}

					MimeBodyPart attachment = new MimeBodyPart();
					DataSource source = new FileDataSource(filePath);
					attachment.setDataHandler(new DataHandler(source));
					attachment.setFileName(fileName);


					MimeMultipart multipart = new MimeMultipart();
					multipart.addBodyPart(body);
					multipart.addBodyPart(attachment);
					
					File reportFolder =
							new File(reportFolderPath);

					if (reportFolder.exists())
					{

						File[] soapUILogFiles = reportFolder.listFiles();

						if (soapUILogFiles.length != 0)
						{

							for (File soapUILogFile : soapUILogFiles)
							{

								if (soapUILogFile.getName().toLowerCase().endsWith(".txt"))
								{
									MimeBodyPart txtFileAttachment = new MimeBodyPart();
									DataSource txtFileSource = new FileDataSource(soapUILogFile.toString());
									txtFileAttachment.setDataHandler(new DataHandler(txtFileSource));
									txtFileAttachment.setFileName(soapUILogFile.getName());
									multipart.addBodyPart(txtFileAttachment);

								}
							}
						}
					}
					
					
					if (message.contains("<html>"))
					{
						msg.setContent(multipart, "text/html");
					}
					else
					{
						msg.setContent(multipart, "text/plain");
					}

					Transport.send(msg);

				}
			}
		}
		catch (Exception exception)
		{
			// Logging the error if there is error in sending mail
			exception.printStackTrace();

		}
	}

}
