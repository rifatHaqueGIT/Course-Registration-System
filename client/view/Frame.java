package client.view;

import java.awt.*;
import javax.swing.*;

/**
 * This class is used to construct the main menu the client interacts with.
 * 
 * @author Bhavdeep Purba, Moiz Abdullah, Rifat Haque
 * @version 1.0
 * @since 6/04/2020
 */
public class Frame extends JFrame {

	/**
	 * Different buttons to be displayed on the main menu
	 */
	public JButton search, addCourse, removeCourse, viewCatalogue, viewStudent;

	/**
	 * Constructs a Frame object.
	 */
	public Frame() {
		super("Course Registration System");

		search = new JButton("Search course catalogue");
		addCourse = new JButton("Enroll student in course");
		removeCourse = new JButton("Remove student from course");
		viewCatalogue = new JButton("View course catalogue");
		viewStudent = new JButton("View all courses taken by students");

		setLayout(new BorderLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add("North", new JLabel("An Application to Maintain Student Records"));

		add("North", viewCatalogue);
		add("East", addCourse);
		add("West", removeCourse);
		add("Center", search);
		add("South", viewStudent);

		setVisible(true);
		pack();

	}

}
