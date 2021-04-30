package client.controller;
import client.view.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * Interacts with the main menu button event listeners and uses methods of ClientController to produce appropriate output.
 * @author Bhavdeep Purba, Moiz Abdullah, Rifat Haque
 * @version 1.0
 * @since 13/04/2020
 *
 */
public class GUIController {
	
	/**
	 * the controller of the client
	 */
	public ClientController client;
	/**
	 * the main menu (Frame object)
	 */
	public Frame myView;
	
	/**
	 * Constructs a GUIController object
	 * @param client the client controller
	 * @param view the main menu (Frame object)
	 */
	public GUIController(ClientController client, Frame view)
	{
		this.client = client;	
		this.myView = view;
	}
	
	/**
	 * Contains all of the Action listeners for the buttons in the main menu. Produces appropriate output using
	 * ClientController methods
	 */
	public void DoThingsWithGUI ()
	{	
		myView.search.addActionListener((ActionEvent e) -> {
			String str = "6 1 ";
			String courseName= JOptionPane.showInputDialog("Please enter the course name: ").toUpperCase();
			str+= courseName;
			String courseId;
			
			while (true)
			{
				try {
					 courseId = JOptionPane.showInputDialog("Please enter the course number: ");
					 int i = Integer.parseInt(courseId);
					 break;
				}catch(NumberFormatException ex)
				{
					
				}
			}
			
			str+=" "+courseId;		
			client.socketOut.println(str);	
			
			if (client.recieveInteger()==1) 
				JOptionPane.showMessageDialog(null, "Course not found");
			
			else 
			{
				client.socketOut.println("1 "+courseName+" "+courseId);
				JOptionPane.showMessageDialog(null, client.recieveInput());					
			}
		});
		myView.addCourse.addActionListener((ActionEvent e) -> {
							
			client.socketOut.println("5");
			String response = client.recieveInput();
			showInfo(response);
			
			String studentName= JOptionPane.showInputDialog("Please enter the student name: ").toUpperCase();
			client.socketOut.println("6 2 "+studentName);
			int exists = client.recieveInteger();
			if (exists==1)
				JOptionPane.showMessageDialog(null, "Student not found");
			else 
			{
				client.socketOut.println("6 3 "+studentName);
				int studentEnroll = client.recieveInteger();
				if (studentEnroll ==1)				
					JOptionPane.showMessageDialog(null, "Students can enroll in a max of 6 courses");		
				else
				{
					String courseName= JOptionPane.showInputDialog("Please enter the course name: ").toUpperCase();
					String courseNumber = JOptionPane.showInputDialog("Please enter the course number: ");
					String section = JOptionPane.showInputDialog("Section 1 or 2?");
					
					if (courseName.equals("")) {
						courseName = "-+-";
					}
					if (courseNumber.equals("")) {
						courseNumber = "-1";
					}
					if (section.equals("")) {
						section= "-1";
					}
					client.socketOut.println("6 5 "+studentName+" "+courseName+" "+courseNumber+" "+section);
					int enroll = client.recieveInteger();
					
					if (enroll == 0) {
						client.socketOut.println("2 "+studentName+" "+courseName+" "+courseNumber+" "+section);
						JOptionPane.showMessageDialog(null, "Course added successfully!");
						
						client.socketOut.println("5");
					    response = client.recieveInput();
						showInfo(response);
					}
					else if (enroll == 1) {
						JOptionPane.showMessageDialog(null, "Course not found!");
					} else if (enroll == 2) {
						JOptionPane.showMessageDialog(null, "Section not available");
					} else if (enroll == 3) {
						JOptionPane.showMessageDialog(null, "Student is already in the course");
					}
				}
			}
			
		});
		myView.removeCourse.addActionListener((ActionEvent e) -> {
			
			client.socketOut.println("5");
			String response = client.recieveInput();
			showInfo(response);
			
			String studentName= JOptionPane.showInputDialog("Please enter the student name: ").toUpperCase();
			client.socketOut.println("6 2 "+studentName);
			int exists = client.recieveInteger();
			if (exists==1)
				JOptionPane.showMessageDialog(null, "Student not found");
			else
			{
				String courseName= JOptionPane.showInputDialog("Please enter the course name: ").toUpperCase();
				String courseNumber = JOptionPane.showInputDialog("Please enter the course number: ");
				
				client.socketOut.println("6 1 "+courseName+" "+courseNumber);
				int courseExists = client.recieveInteger();
				if (courseExists ==1)
					JOptionPane.showMessageDialog(null, "Course not found");
				else
				{
					client.socketOut.println("3 "+studentName+" "+courseName+" "+courseNumber);
					int removed = client.recieveInteger();
					if (removed ==1)
						JOptionPane.showMessageDialog(null, "Student not enrolled in course");
					else if(removed==0)
					{
						JOptionPane.showMessageDialog(null, "Course removed!");
						client.socketOut.println("5");
					    response = client.recieveInput();
						showInfo(response);
					}
				}
			}
		});
		myView.viewCatalogue.addActionListener((ActionEvent e) -> {
		
			client.socketOut.println("4");
			String response = client.recieveInput();
			showInfo(response);
		});
		myView.viewStudent.addActionListener((ActionEvent e) -> {
					
			client.socketOut.println("5");
			String response = client.recieveInput();		
			showInfo(response);
			
		});
	}
	
	/**
	 * Displays output to client that is too large to fit on an information dialog. 
	 * @param info the String object
	 */	
	public void showInfo(String info)
	{
		JTextArea c = new JTextArea(20,20);		 
		JScrollPane scroll = new JScrollPane();
		c.setText(info);
	    scroll.getViewport().setView(c);
	    JOptionPane.showMessageDialog(null, scroll);
	}
	
}
