public class LittleObject {
    private String type, value, id;
    public static String TYPE_STRING = "STRING";
    public static String TYPE_INT = "INT";
    public static String TYPE_FLOAT = "FLOAT";

    public LittleObject(String type, String value){
        this.type = type;
        this.value = value;
    }

    public String getType(){
        return type;
    }

    public String getValue(){
        return value;
    }

    public String getId(){return id;}
}
