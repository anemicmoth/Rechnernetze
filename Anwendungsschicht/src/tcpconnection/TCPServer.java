package tcpconnection;

import codec.Codec;
import codec.message.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer<ClientSocket>
{
    private boolean connected;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private ServerSocket serverSocket;

    public TCPServer()
    {
        try {
            this.serverSocket = new ServerSocket(27999);
            System.out.println("Warte auf Client...");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run_forever()  {
        while(true){
            try {
                this.socket = this.serverSocket.accept();
                this.connected = true;
                System.out.printf("Client hat sich verbunden: %s%n", socket.getRemoteSocketAddress());

                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            handleRequests(socket, in, out);
        }
    }

    private void handleRequests(final Socket socket, final DataInputStream in, final DataOutputStream out)
    {
        while (this.connected)
        {

            try
            {
                //String line = reader.readLine();
                int size = in.readInt();
                byte[] bytes = new byte[size];
                in.readFully(bytes);

                if (bytes.length != 0) {
                    String line = Codec.decode(bytes).toString();

                    System.out.printf("Vom Client (%s) empfangen: %s%n", socket.getRemoteSocketAddress(), line);

                    Message msg = new Message(line);
                    byte[] payload = Codec.encode(msg);

                    if(payload.length > 65_536) throw new IOException("Frame too lengthy.");
                    out.writeInt(payload.length);
                    out.write(payload);
                    out.flush();
                }
                else{
                    connected = false;
                }
            }
            catch (IOException e)
            {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    public static void main(String[] args)
    {
        final TCPServer s = new TCPServer();
        s.run_forever();

    }
}
