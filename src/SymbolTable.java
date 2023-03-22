import java.util.HashMap;

public class SymbolTable {
    private String name;
    public HashMap<String, Object> symbolTableMap = new HashMap<String, Object>();
    public SymbolTable(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addEntry(String id, LittleObject object){
        symbolTableMap.put(id, object);
    }
}
