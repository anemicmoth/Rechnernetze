package bank;

import codec.message.*;

import java.util.ArrayList;
import java.util.List;

public class CentralComputer {
    private boolean userLoggedIn;
    private double testUserFunds;

    public CentralComputer(){
        this.testUserFunds = 45_000;
    }

    public Message handleRequest(Message request){
        List<String> params = request.parameters();
        boolean paramsExist = (params != null && params.size() >= 2);

        switch(request.type()){
            case AUTH:
                
                if(paramsExist && params.getFirst().equals("1234567") && params.getLast().equals("8910"))
                    return new Message(MessageType.OK, null, null, null);
                else
                    return new Message(MessageType.FAILED, null, MessageError.PIN_BLOCK_WRONG, null);
            
            case BALANCE:
                if(!userLoggedIn) return new Message(MessageType.FAILED, null, MessageError.NO_USER_LOGGED_IN, null);
                List<String> p = new ArrayList<>();
                p.add("25000");

                return new Message(MessageType.OK, null, null, p);

            case WITHDRAW:
                if(!userLoggedIn) return new Message(MessageType.FAILED, null, MessageError.NO_USER_LOGGED_IN, null);
                double amount = 0;

                try{
                    amount = Double.parseDouble(request.parameters().getFirst());
                } catch (NumberFormatException e){
                    System.err.println(e.getMessage());
                }
                if(amount > testUserFunds) return new Message(MessageType.FAILED, null, MessageError.NO_FUNDS, null);
                
                return new Message(MessageType.OK, MessageStatus.APPROVED, null, null);
        
            default: return null;
        }

    }
}
