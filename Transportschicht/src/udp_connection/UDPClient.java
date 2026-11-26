package udp_connection;

import java.io.*;
import java.net.*;

class UDPClient {
	public static void main(String args[]) throws Exception {

		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(
				System.in));

		DatagramSocket clientSocket = new DatagramSocket();

		InetAddress IPAddress = InetAddress.getByName("localhost");

		byte[] sendData;
		byte[] receiveData = new byte[1024];

		while(true){

			String sentence = inFromUser.readLine() + "\n";
			sendData = sentence.getBytes();

			DatagramPacket sendPacket = new DatagramPacket(sendData,
					sendData.length, IPAddress, 9876);

			clientSocket.send(sendPacket);
			long sendTime = System.currentTimeMillis();

			DatagramPacket receivePacket = new DatagramPacket(receiveData,
					receiveData.length);

			clientSocket.receive(receivePacket);
			long receiveTime = System.currentTimeMillis();
			long rtt = receiveTime -sendTime;


			String modifiedSentence = new String(receivePacket.getData());
			String printableSentence = modifiedSentence.trim();

			System.out.println(printableSentence);
			System.out.println("RTT: " + rtt + "ms");
			if(!printableSentence.equals("pong"))
				break;
		}
				clientSocket.close();
	}
}
