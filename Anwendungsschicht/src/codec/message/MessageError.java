package codec.message;

public enum MessageError {
    NO_FUNDS("NO_FUNDS"),
    PIN_BLOCK_WRONG("PIN_BLOCK_WRONG"),
    NO_USER_LOGGED_IN("NO_USER_LOGGED_IN");


    private final String error;

    MessageError(String error){
        this.error = error;
    }

    public String getError(){ return error; }

    public static MessageError tryGetValue(String str){
        try{
            return MessageError.valueOf(str);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
