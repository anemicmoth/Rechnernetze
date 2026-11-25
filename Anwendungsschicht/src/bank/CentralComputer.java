package bank;

import codec.message.*;

import java.util.ArrayList;
import java.util.List;

public class CentralComputer {
    private boolean userLoggedIn;
    private double testUserFunds;
    private double lastWithdraw;

    public CentralComputer(){
        this.testUserFunds = 45_000;
    }

    public Message handleRequest(Message request){
        List<String> params = request.parameters();
        boolean paramsExist = (params != null && params.size() >= 2);

        switch(request.type()){
            case AUTH:
                
                if(paramsExist && params.getFirst().equals("1234567") && params.getLast().equals("8910")){
                    userLoggedIn = true;
                    return new Message(MessageType.OK, null, null, null);
                }
                else{
                    return new Message(MessageType.FAILED, null, MessageError.PIN_BLOCK_WRONG, null);
                }
            
            case BALANCE:
                if(!userLoggedIn) return new Message(MessageType.FAILED, null, MessageError.NO_USER_LOGGED_IN, null);
                List<String> p = new ArrayList<>();
                p.add(String.valueOf(testUserFunds));

                return new Message(MessageType.OK, null, null, p);

            case WITHDRAW:
                if(!userLoggedIn) return new Message(MessageType.FAILED, null, MessageError.NO_USER_LOGGED_IN, null);
                lastWithdraw = 0;

                try{
                    lastWithdraw = Double.parseDouble(request.parameters().getFirst());
                } catch (NumberFormatException e){
                    System.err.println(e.getMessage());
                }
                if(lastWithdraw > testUserFunds) return new Message(MessageType.FAILED, null, MessageError.NO_FUNDS, null);
                
                testUserFunds-=lastWithdraw;
                return new Message(MessageType.OK, MessageStatus.APPROVED, null, null);
            case DISPENSE:
                if(lastWithdraw > 0){
                    List<String> returnParams = new ArrayList<>();
                    returnParams.add(String.valueOf(lastWithdraw));
                    lastWithdraw = 0;
                    return new Message(MessageType.OK, MessageStatus.COMMITTED, null, returnParams);
                }
                else{
                    return new Message(MessageType.FAILED, null, null, null);
                }

            default: return new Message(MessageType.FAILED, null, null, null);
        }

    }
}
