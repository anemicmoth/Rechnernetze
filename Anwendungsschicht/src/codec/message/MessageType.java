package codec.message;

public enum MessageType {
    AUTH("AUTH"),
    BALANCE("BALANCE"),
    WITHDRAW("WITHDRAW"),
    DISPENSE("DISPENSE"),
    QUIT("QUIT"),
    OK("OK"),
    FAILED("FAILED");


    private final String message;

    MessageType(String message){
        this.message = message;
    }

    public String getMessage(){ return message; }

    public static MessageType tryGetValue(String str){
        try{
            return MessageType.valueOf(str);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

}
