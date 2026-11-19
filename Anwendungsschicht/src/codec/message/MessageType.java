package codec.message;

public enum MessageType {
    AUTH("AUTH"),
    BALANCE("BALANCE"),
    WITHDRAW("WITHDRAW"),
    DISPENSE("DISPENSE"),
    QUIT("QUIT"),
    OK("OK"),
    APPROVED("APPROVED"),
    COMMITTED("COMMITTED"),
    FAILED("FAILED");

    private final String message;

    MessageType(String message){
        this.message = message;
    }

    public String getMessage(){ return message; }

}
