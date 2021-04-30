package server.model;

import java.util.ArrayList;

/**
 * This class uses methods and data fields to keep track of all courses.
 * 
 * @author Bhavdeep Purba, Moiz Abdullah, Rifat Haque version 1.0
 * @since 6/04/2020
 *
 */
public class CourseCatalogue {
	/**
	 * an ArrayList of the courses
	 */
	private ArrayList<Course> courseList;

	/**
	 * Constructs a CourseCatalogue object
	 */
	public CourseCatalogue() {
		loadFromDataBase();
	}

	/**
	 * Lists all the courses that have at least 8 students enrolled.
	 */
	public void printRunableCourses() {
		System.out.println("\nEach course must have 8 students to be able to run.");
		System.out.println("Here is a list of all the courses, their student number and if they can run.\n");

		for (int i = 0; i < courseList.size(); i++) {
			System.out.println(
					"Course Name: " + courseList.get(i).getCourseName() + " " + courseList.get(i).getCourseNum());
			System.out.println(
					"There are a total of " + courseList.get(i).registratedStudents() + " registrated students.");
			if (courseList.get(i).registratedStudents() >= 8) {
				System.out.println("This course is able to run!");
			} else {
				System.out.println("This course have too little students to run!");
			}
			System.out.println("\n");
		}
	}

	/**
	 * load the course offerings from the data base, cause we haven't made this
	 * program able to read from a file yet.
	 */
	private void loadFromDataBase() {
		DBManager db = new DBManager();
		setCourseList(db.readFromDataBase());
	}

	/**
	 * Creates offering for a course.
	 * 
	 * @param c      the course
	 * @param secNum section number
	 * @param secCap section capacity.
	 */
	public void createCourseOffering(Course c, int secNum, int secCap) {
		if (c != null) {
			CourseOffering theOffering = new CourseOffering(secNum, secCap);
			c.addOffering(theOffering);
		}
	}

	/**
	 * Searches the catalogue for a specific course.
	 * 
	 * @param courseName course name
	 * @param courseNum  course number
	 * @return the course
	 */
	public Course searchCat(String courseName, int courseNum) {
		for (Course c : courseList) {
			if (courseName.equals(c.getCourseName()) && courseNum == c.getCourseNum()) {
				return c;
			}
		}
		displayCourseNotFoundError();
		return null;
	}

	private void displayCourseNotFoundError() {

		System.err.println("Course was not found!");

	}

	public ArrayList<Course> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Course> courseList) {
		this.courseList = courseList;
	}

	@Override
	public String toString() {
		String st = "\nAll courses in the catalogue: \n";
		for (Course c : courseList) {
			st += c; // This line invokes the toString() method of Course
			st += "\n";
		}
		st += "\n";
		return st;
	}

	/**
	 * searches for a specific course
	 * 
	 * @param courseName name of course
	 * @param id         id of course
	 * @return the course
	 */
	public Course getCourse(String courseName, int id) {
		for (int i = 0; i < courseList.size(); i++) {
			if (courseName.toLowerCase().equals(courseList.get(i).getCourseName().toLowerCase())
					&& id == courseList.get(i).getCourseNum()) {
				return courseList.get(i);
			}
		}
		return null;
	}

	/**
	 * return all the courses in the catalogue
	 * 
	 * @return the courses in the catalogue.
	 */
	public String searchCatalogueCourses() {
		String s = "";
		for (int i = 0; i < courseList.size(); i++) {
			s += courseList.get(i).searchCatalogueCourses();
		}
		return s;
	}

}
