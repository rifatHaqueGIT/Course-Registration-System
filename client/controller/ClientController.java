package client.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import client.view.Frame;

/**
 * Provides main communication with Server controller.
 * 
 * @author Bhavdeep Purba, Moiz Abdullah, Rifat Haque
 * @version 1.0
 * @since 13/04/2020
 *
 */

public class ClientController {

	/**
	 * Socket for the client
	 */
	public Socket socket;
	/**
	 * SocketIn for client input
	 */
	public BufferedReader socketIn;
	/**
	 * Socket out for client output
	 */
	public PrintWriter socketOut;

	/**
	 * Constructs the controller object.
	 * 
	 * @param serverName the name of the server
	 * @param portNumber the number of the port
	 */
	public ClientController(String serverName, int portNumber) {
		try {
			socket = new Socket(serverName, portNumber);

			socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			socketOut = new PrintWriter((socket.getOutputStream()), true);
		} catch (IOException e) {
			System.err.println(e.getStackTrace());
		}
	}

	/**
	 * Receives input from the server.
	 * 
	 * @return a String object
	 */
	public String recieveInput() {
		try {
			return socketIn.readLine().replace('_', '\n');
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(1);
		return "error!";
	}

	/**
	 * Receives an integer from server.
	 * 
	 * @return the integer
	 */
	public int recieveInteger() {
		try {
			return Integer.parseInt(socketIn.readLine().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.exit(1);
		return -1;
	}

	public static void main(String[] args) throws IOException {

		ClientController client = new ClientController("localhost", 8099);

		Frame myView = new Frame();

		GUIController myController = new GUIController(client, myView);

		myController.DoThingsWithGUI();
	}

}
