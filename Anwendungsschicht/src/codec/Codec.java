package codec;

import codec.message.Message;
import codec.message.MessageType;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class Codec {

    public static byte[] encode(Message msg){
        String s = msg.type().getMessage() + " " + msg.parameters();

        return s.getBytes(StandardCharsets.UTF_8);
    }

    public static Message decode(byte[] data){
        String s = new String(data, StandardCharsets.UTF_8);
        String[] parts = s.split(" ");

        MessageType type = null;
        List<String> parameters = new ArrayList<>();

        for(String part:parts){
            if(type == null){
                type = MessageType.valueOf(part);
            }
            else{
                parameters.add(part);
            }
        }
        return new Message(type, parameters);
    }
}
