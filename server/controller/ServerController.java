package server.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import server.model.Model;

/**
 * This class provides the main communication with the Client controller.
 * @author Bhavdeep Purba, Moiz Abdullah, Rifat Haque
 * @since 6/04/2020
 *
 */
public class ServerController {
	
	/**
	 * Socket used for connection
	 */
	private Socket socket;
	/**
	 * the server socket to connect with the port
	 */
	private ServerSocket serverSocket;
	/** 
	 * BufferedReader object used for input to the server
	 */
	
	/**
	 * Constructs a ServerController object.
	 * @param port the port to connect to
	 */
	
	private ExecutorService pool;
	public ServerController(int port) throws IOException {
	
		serverSocket = new ServerSocket(port);
		pool = Executors.newCachedThreadPool();
	}
	
	/**
	 * Sends a String message to the client.
	 * @param message the String object
	 */
	
	
	/**
	 * Splits the data received from user at a space
	 * @param data the raw data
	 * @return processed data
	 */
	
	
	public void establishConnection() throws IOException {
		while(true)
		{
			socket = serverSocket.accept();
			DBController controller = new DBController(socket, this, new Model());
			pool.execute(controller);
		}
	}

	public static void main (String [] args) throws IOException{
		
		ServerController myServer = new ServerController (8099);
		myServer.establishConnection();
		
		
		
}
}
