package server.model;

import java.util.ArrayList;
/**
 * this class is similar to our data base class, as it adds and creates all of the course offering
 * for our courses and the students. The server talks to this class which controls everything happening 
 * in the backend.
 * @author Bhavdeep Purba, Moiz Abdullah, Rifat Haque
 * @version 1.0
 * @since 13/04/2020
 *
 */
public class Model {
	private CourseCatalogue cat;
	private ArrayList<Student> students;
	
	public Model () {
		cat = new CourseCatalogue ();		
		DBManager db = new DBManager();
		students = db.readFromDataBase2();
	
		
		//SOME EXAMPLE INPUTS
		Course myCourse = cat.searchCat("ENGG", 233);
		cat.createCourseOffering(myCourse, 1, 34);
		cat.createCourseOffering(myCourse, 2, 38);
		
		myCourse = cat.searchCat("ENSF", 409);
		cat.createCourseOffering(myCourse, 1, 42);
		cat.createCourseOffering(myCourse, 2, 61);
		
		myCourse = cat.searchCat("PHYS", 259);
		cat.createCourseOffering(myCourse, 1, 26);
		cat.createCourseOffering(myCourse, 2, 240);
		
		myCourse = cat.searchCat("MATH", 271);
		cat.createCourseOffering(myCourse, 1, 31);
		cat.createCourseOffering(myCourse, 2, 33);
		
		myCourse = cat.searchCat("ENCM", 275);
		cat.createCourseOffering(myCourse, 1, 76);
		cat.createCourseOffering(myCourse, 2, 51);
		
		myCourse = cat.searchCat("CPSC", 332);
		cat.createCourseOffering(myCourse, 1, 46);
		cat.createCourseOffering(myCourse, 2, 251);
		
	}
	/**
	 * if course exists, return 0, else 1.
	 * @param courseName name of course
	 * @param courseID ID of course
	 * @return 1 or 0
	 */
	public String courseExist(String courseName, int courseID) {
		if (getCourse(courseName, courseID)==null) {
			return ("1");
		}
		return ("0");
	}
	/**
	 * if student exists, return 0, else 1
	 * @param studentName name of student
	 * @return 0 or 1
	 */
	public String studentExist(String studentName) {
		if (getStudent(studentName)==null) {
			return ("1");
		}
		return ("0");
	}
	/**
	 * if student can enroll in any class, has less than 6 classes, return 0, else 1
	 * @param studentName name of student
	 * @return 0 ot 1
	 */
	public String studentCanEnroll(String studentName) {
		if (getStudent(studentName).full()==true) {
			return ("1");
		}
		return ("0");
	}
	/**
	 * remove a course from a student
	 * @param studentName name of student 
	 * @param courseName name of course
	 * @param courseID id of course
	 * @return 0 if course removed, 1 otherwise.
	 */
	public String removeCourse(String studentName, String courseName, int courseID) {
		Student student = getStudent(studentName);
		if (student.deleteOffering(courseName,courseID)==false) {
			//Course was not taken by student
			return ("1");
		} 
		return ("0");
	}
	/**
	 * checks to see if student can enroll in course
	 * @param studentName name of student
	 * @param courseName course name
	 * @param courseID course ID
	 * @param sectionID section ID
	 * @return a digit, depends on the outcome. refer to comments
	 */
	public String canEnroll(String studentName, String courseName, int courseID, int sectionID) {
		Course course = getCourse(courseName, courseID);

		if (course==null) {
			//No such course exist in the system
			return ("1");
		}
		
		CourseOffering courseOffering = course.getCourseOfferingAt(sectionID);
		
		if (courseOffering==null) {
			//Section not available
			return ("2");
		}
		
		Student student = getStudent(studentName);
		
		if (student.alreadyEnrolled(courseName, courseID)) {
			//Student already in the course
			return ("3");
		}
		
		//Can Enroll
		return ("0");
	}
	
	/**
	 * searches for course in the catalogue
	 * @param courseName course name
	 * @param courseID course ID
	 * @return the course
	 */
	public String searchCatalogue(String courseName, int courseID) {
		Course course = getCourse(courseName, courseID);
		return (""+course);
	}
	/**
	 * adds a course to a student
	 * @param studentName student name
	 * @param courseName course name
	 * @param courseID course ID
	 * @param sectionID section number
	 */
	public void addCourse(String name, String courseName, int number, int section) {
		Student s = getStudent(name);
		
		Course course = getCourse(courseName, number);

		CourseOffering Offering = course.getCourseOfferingAt(section);

		s.addRegistration(new Registration(s,Offering));
	}
	/**
	 * returns the catalogue
	 * @return the cataloque
	 */
	public String viewCatalogue() {
		return (""+cat);
	}
	/**
	 * returns all courses taken by the students 
	 * @return the list all courses taken.
	 */
	public String viewCourseTaken() {
		String courses = "";
		for (int j=0; j<students.size();j++) {
			courses += students.get(j).viewStudentCourses() + "\n";
		}
		return (""+courses);
	}
	/**
	 * gives the list of students
	 * @return the list
	 */
	public String viewStudentList() {
		String list ="";
		for (Student s: students) {
			list += s + "\n";
		}
		return list;
	}
	/**
	 * return the student you are asking for, else null
	 * @param name name of the student you want.
	 * @return the student, or null
	 */
	private Student getStudent(String n) {
		for (int j=0; j< students.size(); j++) {
			if (students.get(j).getStudentName().toLowerCase().equals(n.toLowerCase())) {
				return students.get(j);
			}
		}
		return null;
	}
	/**
	 * gets a course if it exists
	 * @param courseName the name of the course
	 * @param id the id of the course
	 * @return the course
	 */
	private Course getCourse(String courseName, int id) {
		return cat.getCourse(courseName, id);
	}
}
