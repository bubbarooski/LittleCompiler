import java.util.HashMap;

public class SymbolTable {
    private String name;
    public HashMap<String, LittleObject> symbolTableMap = new HashMap<>();
    public SymbolTable(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void addEntry(String id, LittleObject object){
        symbolTableMap.put(id, object);
    }

    public void printTable(){
        System.out.println("Symbol table " + name);
        for(String key : symbolTableMap.keySet()){
            System.out.print("name " + key + " type " + symbolTableMap.get(key).getType());
            if(symbolTableMap.get(key).equals(LittleObject.TYPE_STRING))
                System.out.print(" value \"" + symbolTableMap.get(key).getValue() + "\"");

            System.out.println();
        }
    }
}
