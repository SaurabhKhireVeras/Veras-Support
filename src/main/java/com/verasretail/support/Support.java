
package com.verasretail.support;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.codoid.products.fillo.Connection;
import com.verasretail.support.Constants;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;

public class Support
{

	public static Connection connection = null;
	private static JFrame mainFrame;
	public static final String CONFIG_FILE = "configurations.properties";

	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String args[])
	{
		EventQueue.invokeLater(new Runnable()
			{
				public void run()
				{

					try
					{

						
						JFrame mainFrame = new JFrame("");
					    mainFrame.setBackground(Color.WHITE);
						mainFrame.setSize(1300, 685);
						mainFrame.setVisible(true);
						mainFrame.setLayout(null);
						
						JButton jWriteButton = new JButton("Write Files");
						jWriteButton.setBounds(50,100,95,30);  
					    jWriteButton.setSize(100,50);  
					    jWriteButton.setBackground(Color.BLUE);
					    jWriteButton.setVisible(true); 
					    mainFrame.add(jWriteButton);  
			
							
						JButton jReplaceButton = new JButton("Move Files");
						jReplaceButton.setBounds(50,200,95,30);  
						jReplaceButton.setSize(100,50);  
						jReplaceButton.setBackground(Color.BLUE);
						mainFrame.setLayout(null);
						jReplaceButton.setVisible(true); 
					    mainFrame.add(jReplaceButton);  
		
					    jWriteButton.addActionListener(actionEvent ->
						{
							try
							{

								
							}
							catch (Exception ex)
							{

							}
						});
					    
					    jReplaceButton.addActionListener(actionEvent ->
						{
							try
							{
								JFrame moveFrame = new JFrame("");
								moveFrame.setBackground(Color.WHITE);
								moveFrame.setSize(600, 400);
								moveFrame.setVisible(true);
								moveFrame.setLayout(null);
								
								JLabel sourcePathLabel = new JLabel("Source Path");
								sourcePathLabel.setBounds(50,50,95,30);  
								sourcePathLabel.setSize(100,50);  
								sourcePathLabel.setBackground(Color.BLUE);
								sourcePathLabel.setVisible(true); 
							    moveFrame.add(sourcePathLabel);  
							    
							    JLabel destPathLabel = new JLabel("Destination Path");
							    destPathLabel.setBounds(50,100,95,30);  
							    destPathLabel.setSize(100,50);  
							    destPathLabel.setBackground(Color.BLUE);
							    destPathLabel.setVisible(true); 
							    moveFrame.add(destPathLabel);  
							    
							    JTextArea sourcePathText = new JTextArea("");
							    sourcePathText.setBounds(200,50,65,30);  
							    sourcePathText.setSize(100,30);  
							    sourcePathText.setBackground(Color.WHITE);
							    sourcePathText.setVisible(true); 
							    moveFrame.add(sourcePathText);  
							    
							    JTextArea destPathText = new JTextArea("");
							    destPathText.setBounds(200,100,65,30);  
							    destPathText.setSize(100,30);  
							    destPathText.setBackground(Color.WHITE);
							    destPathText.setVisible(true); 
							    moveFrame.add(destPathText);  
					
									
								JButton moveButton = new JButton("Move Files");
								moveButton.setBounds(50,200,95,30);  
								moveButton.setSize(100,50);  
								moveButton.setBackground(Color.BLUE);
								moveButton.setVisible(true); 
								moveFrame.add(moveButton); 
								
								moveButton.addActionListener(actionEvent1 ->
								{
									try
									{
										JFrame progressFrame = new JFrame("");
										progressFrame.setBackground(Color.WHITE);
										progressFrame.setSize(600, 400);
										progressFrame.setVisible(true);
										progressFrame.setLayout(null);
								

									    
									    JLabel progressText = new JLabel("");
									    progressText.setBounds(50,100,95,30);  
									    progressText.setSize(100,50);  
									    progressText.setBackground(Color.WHITE);
									    progressText.setVisible(true); 
									    progressFrame.add(progressText);  
							
											
										JButton okButton = new JButton("Move Files");
										okButton.setBounds(50,200,95,30);  
										okButton.setSize(100,50);  
										okButton.setBackground(Color.BLUE);
										okButton.setVisible(true); 
										progressFrame.add(okButton);  
										
										progressFrame.addWindowListener(new WindowAdapter()
										{
											@Override
											public void windowClosing(WindowEvent windowEvent)
											{
												System.exit(0);
											}
										});

										MoveManyFiles.ReplaceFiles(sourcePathText.getText().toString(), destPathText.getText().toString(), "C:\\Veras-Support\\Veras-Support\\config\\MoveFiles.xlsx", progressText);
									}
									catch (Exception ex)
									{

									}
								});

								
							}
							catch (Exception ex)
							{

							}
						});


		

						mainFrame.addWindowListener(new WindowAdapter()
							{
								@Override
								public void windowClosing(WindowEvent windowEvent)
								{
									System.exit(0);
								}
							});

					}
					catch (Exception exception)
					{
						exception.printStackTrace();

					}
				}
			});
	}
}
