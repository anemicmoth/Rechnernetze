package tcpconnection;

import codec.Codec;
import codec.message.Message;

import java.io.*;
import java.net.Socket;

public class TCPClient
{

    private Socket socket;
    private BufferedWriter writer;
    private BufferedReader reader;
    private DataInputStream in;
    private DataOutputStream out;

    public TCPClient()
    {
        try
        {
            socket = new Socket("localhost", 27999);
            System.out.println("TCPClient connected end with \"END\"");

            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void sendText(final Message msg)
    {
        try
        {
            byte[] payload = Codec.encode(msg);

            if(payload.length > 65_536) throw new IOException("Frame too lengthy.");
            out.writeInt(payload.length);
            out.write(payload);
            out.flush();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public Message receiveText(){
        try{
            int size = in.readInt();
            byte[] bytes = new byte[size];
            in.readFully(bytes);
            return Codec.decode(bytes);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public void handleInput(TCPClient client) throws IOException {
        final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true)
        {
            final String line = reader.readLine();
            Message msg = new Message(line);
            if (line.equals("END")) {
                break;
            }
            client.sendText(msg);
            Message returnMsg = client.receiveText();
            System.out.println(returnMsg.toString());
        }
    }

    public void close()
    {
        try
        {
            socket.shutdownOutput();
            socket.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException
    {
        TCPClient TCPClient = new TCPClient();

            TCPClient.handleInput(TCPClient);
        TCPClient.close();
    }
}
