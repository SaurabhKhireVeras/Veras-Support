package com.verasretail.support;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Properties;
import java.util.stream.Stream;


public class FileUtil
{
	//private static final Logger log = Logger.getLogger(FileUtil.class);

	public static String getFilePath(String absoluteSourcePath, String fileName)
	{

		File jarPath = new File(absoluteSourcePath);
		String propertiesPath = jarPath.getParentFile().getAbsolutePath().replaceAll("%20", " ");
		return propertiesPath + "\\config\\" + fileName;
	}

	public static Properties readPropertiesFile(String fileName)
	{

		Properties prop;

		try
		{
			try (FileInputStream fileInputStream = new FileInputStream(fileName))
			{
				prop = new Properties();
				prop.load(fileInputStream);
			}
		}
		catch (IOException ioException)
		{
			//log.error("Configuration File Error : ", ioException);
			prop = null;
		}

		return prop;

	}

	public static String currentDateTimeForFile()
	{
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
		return dateTimeFormatter.format(LocalDateTime.now());
	}

	public static String currentDateTime()
	{
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		return dateTimeFormatter.format(LocalDateTime.now());
	}

	public static void defaultLoggingProperties()
	{

		try
		{
			/*PatternLayout layout = new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %c{1}:%L - %m%n");
			RollingFileAppender appender = new RollingFileAppender(layout, "logs\\logging.log");
			appender.setMaxFileSize("12MB");
			appender.setMaxBackupIndex(10);
			appender.activateOptions();
			Logger.getRootLogger().addAppender(appender);
			Logger.getRootLogger().setLevel(Level.DEBUG);*/
		}
		catch (Exception ioException)
		{
			//log.error("Default Log Error : ", ioException);
		}
	}

	public static void createFolderIfNotExist(String folderPath)
	{

		try
		{
			File folder = new File(folderPath);
			if (!folder.exists())
			{

				folder.mkdir();

			}

			//log.info("Folder '" + folderPath + "'created for Csv Files");

		}
		catch (Exception ioException)
		{
			// Logging the error if there is error in creating folder

			//log.error("", ioException);
		}

	}

	public static void createFolderAndReplaceIfExist(String folderPath)
	{

		try
		{
			File folder = new File(folderPath);
			Path folderNamePath = Paths.get(folderPath);
			if (folder.exists())
			{
				if (folder.isDirectory())
				{

					try (Stream<Path> filesWalk = Files.walk(folderNamePath))
					{
						filesWalk.sorted(Comparator.reverseOrder()).map(Path::toFile).forEach(File::delete);
					}
					folder.mkdir();

				}
			}
			else
			{

				folder.mkdir();
			}
			//log.info("Folder '" + folderPath + "'created for Csv Files");

		}
		catch (Exception ioException)
		{
			// Logging the error if there is error in creating folder

			//log.error("", ioException);
		}

	}

	public static void moveSoapUILogs(Properties props)
	{

		try
		{
			createFolderAndReplaceIfExist(
					"C:\\API Automation\\API Automation Extend\\Veras-Api-Automation-Framework\\Soap UI Logs\\"
							+ currentDateTimeForFile() + "_Logs");

			File soapUILogFolder =
					new File("C:\\API Automation\\API Automation Extend\\Veras-Api-Automation-Framework");

			if (soapUILogFolder.exists())
			{

				File[] soapUILogFiles = soapUILogFolder.listFiles();

				if (soapUILogFiles.length != 0)
				{

					//log.debug("Moving files");

					for (File soapUILogFile : soapUILogFiles)
					{

						if (soapUILogFile.getName().toLowerCase().endsWith(".txt"))
						{
							Files.move(Paths.get(soapUILogFile.toString()), Paths.get(
									"C:\\API Automation\\API Automation Extend\\Veras-Api-Automation-Framework\\Soap UI Logs\\"
											+ currentDateTimeForFile() + "_Logs\\" + soapUILogFile.getName()));

						}
					}
				}
			}
		}
		catch (Exception exception)
		{
			//log.error("", exception);
		}
	}

}
