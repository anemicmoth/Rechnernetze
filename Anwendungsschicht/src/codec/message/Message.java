package codec.message;

import java.util.ArrayList;
import java.util.List;

public class Message {
    private MessageType msgType = null;
    private MessageStatus msgStatus = null;
    private MessageError msgError = null;
    private List<String> parameters;

    public Message(MessageType msgType, MessageStatus status, MessageError error, List<String> parameters) {
        this.msgType = msgType;
        this.msgStatus = status;
        this.msgError = error;
        this.parameters = (parameters != null && !parameters.isEmpty()) ? 
                new ArrayList<>(parameters):
                new ArrayList<>();
    }

    public Message(String message){
        String[] parts = message.split(" ");
        parameters = new ArrayList<>();
        
        for(String part:parts){

            if(MessageType.tryGetValue(part) != null){
                msgType = MessageType.tryGetValue(part);
            }

            else if (MessageError.tryGetValue(part) != null) {
                msgError = MessageError.tryGetValue(part);
            }

            else if (MessageStatus.tryGetValue(part) != null) {
                msgStatus = MessageStatus.tryGetValue(part);
            }

            else parameters.add(part);
          
        }
    }

    public MessageType type(){ return msgType; }

    public MessageStatus status() { return msgStatus; }

    public MessageError error() { return msgError; }

    public String paramsAsString(){
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

    public List<String> parameters(){ return parameters; }

    @Override
    public String toString(){
        String str = msgType.getMessage();
        if(msgStatus != null) str += " " + msgStatus.getStatus();
        if(msgError != null) str += " " + msgError.getError();
        return str + " " + paramsAsString();
    }
}
