package codec.message;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private MessageType msgType = null;
    private List<String> parameters;

    public Message(MessageType msgType, List<String> parameters) {
        this.msgType = msgType;
        this.parameters = new ArrayList<>(parameters);
    }

    public Message(String message){
        String[] parts = message.split(" ");
        parameters = new ArrayList<>();
        for(String part:parts){
            if(msgType == null){
                msgType = MessageType.valueOf(part);
            }
            else{
                parameters.add(part);
            }
        }
    }

    public MessageType type(){ return msgType; }

    public String parameters(){
        StringBuilder s = new StringBuilder();
        for(int i = 0; i<parameters.size(); i++){
            if(i == parameters.size()-1){
                s.append(parameters.get(i));
            } else {
                s.append(parameters.get(i)).append(" ");
            }
        }
        return s.toString();
    }

    @Override
    public String toString(){
        return msgType.getMessage() + " " + parameters();
    }
}
