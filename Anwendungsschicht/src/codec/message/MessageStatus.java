package codec.message;

public enum MessageStatus {
    APPROVED("APPROVED"),
    COMMITTED("COMMITTED");

    private final String status;

    MessageStatus(String status){
        this.status = status;
    }

    public String getStatus(){ return status; }

    public static MessageStatus tryGetValue(String str){
        try{
            return MessageStatus.valueOf(str);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
