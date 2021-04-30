package server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

import server.model.Model;

/**
 * 
 * @author Bhavdeep Purba, Moiz Abdullah, Rifat Haque
 * @version 1.0
 * @since 6/04/2020
 */

public class DBController implements Runnable{
	
	/**
	 * the server controller
	 */
	private ServerController server;
	
	private Socket socket;
	
	/**
	 * a Model object
	 */
	private Model model;
	private BufferedReader socketIn;
	/**
	 * PrintWriter object used for server output to client
	 */
	private PrintWriter socketOut;
	
	/**
	 * Constructs a DBController object
	 * @param server the ServerController object
	 * @param model the Model object
	 * @throws IOException 
	 */
	public DBController (Socket s, ServerController server, Model model) throws IOException {
		socket = s;
		
		socketIn = new BufferedReader (new InputStreamReader (socket.getInputStream()));
		socketOut = new PrintWriter (socket.getOutputStream(), true);
		
		this.server = server;
		this.model = model;
	}
	
	
	public void communicate(String message) {
		socketOut.println(message.replace('\n', '_'));
	}
	
	/**
	 * Recieves raw input from the client.
	 * @return a String object
	 */
	public ArrayList<String> receiveInput() {
		while (true) {
			try {
				String data = socketIn.readLine();
				return processData(data);
			} catch (IOException e) {
				System.exit(1);
			}
		}
	}
	
	private ArrayList<String> processData(String data) {
		ArrayList<String> input = new ArrayList<String>();
		String[] t = data.split(" ");
		for (int j=0; j < t.length; j++) {
			input.add(t[j]);
		}
		return input;
	}
	
	/**
	 * Calls the appropriate methods of Model based on input and returns output to be displayed to the client.
	 */
	public void run()
	{
		while (true)
		{
			ArrayList<String> input = receiveInput();
			switch (Integer.parseInt(input.get(0))) {
			
			case 1:
				communicate(model.searchCatalogue(input.get(1), Integer.parseInt(input.get(2))));
				break;
				
			case 2:			
				model.addCourse(input.get(1),input.get(2),Integer.parseInt(input.get(3)),Integer.parseInt(input.get(4)));
				break;	
				
			case 3:
				
				communicate(model.removeCourse(input.get(1), input.get(2),Integer.parseInt(input.get(3))));
				break;	
				
			case 4:			
				communicate(model.viewCatalogue());
				break;
			
			case 5:
				communicate(model.viewCourseTaken());
				break;
				
			case 6:			
				switch (Integer.parseInt(input.get(1))) {
				case 1:
					if (input.size()<4) {
						communicate("1");
						break;
					}
					communicate(model.courseExist(input.get(2), Integer.parseInt(input.get(3))));
					break;
				case 2:
					if (input.size()<3) {
						communicate("1");
						break;
					}
					communicate(model.studentExist(input.get(2)));
					break;
				case 3:
					if (input.size()<3) {
						communicate("1");
						break;
					}
					communicate(model.studentCanEnroll(input.get(2)));
					break;
				case 5:
					communicate(model.canEnroll(input.get(2), input.get(3), Integer.parseInt(input.get(4)),Integer.parseInt(input.get(5))));
					break;
				}
				break;
			
			}
		}
	}

}
