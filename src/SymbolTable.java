import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private String name;
    public HashMap<String, LittleObject> symbolTableMap = new HashMap<>();
    private ArrayList<String> keys = new ArrayList<>();
    public SymbolTable(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addEntry(String id, LittleObject object){
        keys.add(id);
        symbolTableMap.put(id, object);
    }

    public void printTable(){
        System.out.println("Symbol table " + name);
        for(String key : keys){
            System.out.print("name " + key + " type " + symbolTableMap.get(key).getType());
            if(symbolTableMap.get(key).getType().equals(LittleObject.TYPE_STRING))
                System.out.print(" value " + symbolTableMap.get(key).getValue());

            System.out.println();
        }
    }
}
