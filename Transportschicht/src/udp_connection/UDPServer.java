package udp_connection;

import java.net.*;

class UDPServer {
	public static void main(String args[]) throws Exception {

		DatagramSocket serverSocket = new DatagramSocket(9876);


		while (true) {

			byte[] receiveData = new byte[1024];
			byte[] sendData;

			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

			serverSocket.receive(receivePacket);

			String sentence = new String(receivePacket.getData());
			sentence = sentence.trim();

			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();

			String pong = "pong";

			if(sentence.equals("ping")){
				sendData = pong.getBytes();
			}
			else{
				sendData = ("failed").getBytes();
			}
				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);

			serverSocket.send(sendPacket);
		}
	}
}
