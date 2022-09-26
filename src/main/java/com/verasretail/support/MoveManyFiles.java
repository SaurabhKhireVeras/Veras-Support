package com.verasretail.support;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;
import org.apache.commons.io.FileUtils;

public class MoveManyFiles
{
	public static String path;
	
	public static void ReplaceFiles(String mainFilePath, String sourceFilePath, String fileEntriesPath, JLabel progressText) {
	
		try {
	
	Fillo fillo=new Fillo();
	Connection connection=fillo.getConnection(fileEntriesPath);
	String inputFileName = "";
	String outputFileName = "";
	
	progressText.setText("In Progress...");
	
	String strQuery="Select * from MoveFilesSheet";

   Recordset recordset=connection.executeQuery(strQuery);

	while(recordset.next()){

	
		inputFileName = recordset.getField("InputFiles");
		outputFileName = recordset.getField("OutputFiles");
		
		File mainFolder = new File(mainFilePath);
		
		
		findAllFilesInFolder(mainFolder, inputFileName, outputFileName);
				if(!path.equals("")) {
					
					String[] outputFilesList = outputFileName.split(",");
					
					for(int fileCount = 0; fileCount>=outputFilesList.length; fileCount++) {
											
					File  pathSource = new File (sourceFilePath + "\\" + outputFilesList[fileCount]);
					File  pathDest = new File (path + "\\" + outputFilesList[fileCount]);
					System.out.println(inputFileName+"/"+outputFilesList[fileCount] + " : " + pathDest);
					FileUtils.copyFile(pathSource, pathDest);
					}
				} 

	
	} 
	recordset.close();
	
	progressText.setText("Complete");
	
		} catch (Exception exception) {
			progressText.setText("Error");
		}

}




public static void findAllFilesInFolder(File folder, String inputFileName, String outputFileName) {
	
	try {
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) {
				
				String[] values = inputFileName.split(",");
				List<String> commaSeparatedExtraColumnNosList = Arrays.asList(values);
				
				if (commaSeparatedExtraColumnNosList.contains(file.getName().toString())) {

					path = folder + "";
					System.out.println(inputFileName+"/"+ " : " + file.getName().toString());
					
					
				}
			} else {
				findAllFilesInFolder(file, inputFileName, outputFileName);
			}
		}
	} catch (Exception ex) {
		ex.printStackTrace();
	}

}
}
