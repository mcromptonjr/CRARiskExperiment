package edu.ucdavis.cs.cra;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/**
 * Pseudo-Database server which echos a request's identifier with a randomized payload of data.
 * 
 * @author Mac Crompton
 *
 */
public class TestServer { 
	
	// The amount of "rows" of data to generate for the database
	private static final int NUM_ROWS = 100;

	public static void main(String[] args) throws IOException, InterruptedException {
		// Generate pseudo-database
		FileWriter fw = new FileWriter(new File("/tmp/database.db"));
		for(int i = 0; i < NUM_ROWS; i++) {
			fw.write(i + "\t" + Math.random() + "\n");
		}
		fw.flush();
		fw.close();
		
		// Open up a UDP server socket for communication of the database
		DatagramSocket serverSocket = new DatagramSocket(52017);
		byte[] receiveData = new byte[1024];
		byte[] sendData = new byte[1024];
		while(receiveData != null)
		{
			// Receive a UDP packet request from a client.
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);

			// Read from pseudo-database
			Scanner read = new Scanner(new File("/tmp/database.db"));
			while(read.hasNextLine()) {
				read.nextLine();
			}
			read.close();
			
			// Set the identifier to return back to sender
			for(int i = 0; i < Long.BYTES; i++) {
				sendData[i] = receiveData[i];
			}
			
			// Generate data to send back to the client
			for(int i = Long.BYTES; i < sendData.length; i++) {
				sendData[i] = (byte) (Byte.MAX_VALUE * Math.random());
			}

			// Get the client to send data back to.
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();

			// Send the client a response
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
		
		serverSocket.close();
	}
}
