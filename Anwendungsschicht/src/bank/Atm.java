package bank;

import codec.message.Message;
import codec.message.MessageType;

public class Atm {
    private double availableFunds;
    private Message lastSentMessage;

    public Atm(double funds){
        this.availableFunds = funds;
    }

    private boolean withdrawAmount(double amount){
        if(amount > availableFunds) return false;
        availableFunds -= amount;
        return true;
    }

    public void setLastSentMessage(Message msg){
        lastSentMessage = msg;
    }

    public boolean handleRequest(Message request){
        // TODO: lastSentMessage als null abfangen
        
        if(lastSentMessage == null){
            setLastSentMessage(request);
            return false;
        }

        switch(lastSentMessage.type()){
            case MessageType.AUTH, MessageType.BALANCE, MessageType.WITHDRAW:
                return request.type() == MessageType.OK;
            
            case MessageType.DISPENSE:{

                if(request.type() != MessageType.OK) return false;

                try{
                    double amount = Double.parseDouble(request.parameters().getFirst());
                    return withdrawAmount(amount);
                } catch (NumberFormatException e){
                    System.err.println(e.getMessage());
                }

            }

            default: return false;
        }
        


    }

}
