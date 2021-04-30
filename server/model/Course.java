package server.model;

import java.util.ArrayList;

/**
 * This class uses methods and data fields to create a data type representing a
 * university course.
 * 
 * @author Bhavdeep Purba, Moiz Abdullah, Rifat Haque
 * @version 1.0
 * @since 6/04/2020
 *
 */
public class Course {
	/**
	 * the name of the course
	 */
	private String courseName;
	/**
	 * the course number
	 */
	private int courseNum;
	@SuppressWarnings("unused")
	/**
	 * an ArrayList of the prerequisite courses
	 */
	private ArrayList<Course> preReq;
	/**
	 * an ArrayList of the course offerings
	 */
	private ArrayList<CourseOffering> offeringList;

	/**
	 * Constructs a Course object.
	 * 
	 * @param courseName the name of the course
	 * @param courseNum  the course number
	 */
	public Course(String courseName, int courseNum) {
		this.setCourseName(courseName);
		this.setCourseNum(courseNum);
		// Both of the following are only association
		preReq = new ArrayList<Course>();
		offeringList = new ArrayList<CourseOffering>();
	}

	/**
	 * Returns the number of registered students.
	 * 
	 * @return registered students
	 */
	public int registratedStudents() {
		int sum = 0;
		for (int i = 0; i < offeringList.size(); i++) {
			sum += offeringList.get(i).registratedStudents();
		}
		return sum;
	}

	/**
	 * Adds an offering to a course.
	 * 
	 * @param offering to add
	 */
	public void addOffering(CourseOffering offering) {
		if (offering != null && offering.getTheCourse() == null) {
			offering.setTheCourse(this);
			if (!offering.getTheCourse().getCourseName().equals(courseName)
					|| offering.getTheCourse().getCourseNum() != courseNum) {
				System.err.println("Error! This section belongs to another course!");
				return;
			}

			offeringList.add(offering);
		}
	}

	/**
	 * Returns the course name.
	 * 
	 * @return the name
	 */
	public String getCourseName() {
		return courseName;
	}

	/**
	 * Sets the course name.
	 * 
	 * @param courseName the name of the course
	 */
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	/**
	 * Returns the number of the course.
	 * 
	 * @return the number
	 */
	public int getCourseNum() {
		return courseNum;
	}

	/**
	 * Sets the number of the course.
	 * 
	 * @param courseNum the number
	 */
	public void setCourseNum(int courseNum) {
		this.courseNum = courseNum;
	}

	/**
	 * Overrides the toString function to print a Course object.
	 */
	@Override
	public String toString() {
		String st = "\n";
		st += getCourseName() + " " + getCourseNum();
		st += "\nAll course sections:\n";
		st += "-------\n";
		for (CourseOffering c : offeringList)
			st += c;
		st += "\n-------\n";
		return st;
	}

	/**
	 * Looks for a specific course offering, and returns it if it exists
	 * 
	 * @param i the section number
	 * @return the offering
	 */
	public CourseOffering getCourseOfferingAt(int i) {
		for (int j = 0; j < offeringList.size(); j++) {
			if (offeringList.get(j).getSecNum() == i) {
				return offeringList.get(j);
			}
		}
		return null;
	}

	/**
	 * Returns all the sections numbers in this course's catalogue.
	 * 
	 * @return all the offerings of this course.
	 */
	public String searchCatalogueCourses() {
		String s = "";
		for (int i = 0; i < offeringList.size(); i++) {
			s += offeringList.get(i).searchCatalogueCourses();
		}

		return s;
	}

}
