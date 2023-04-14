import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class SymbolTable {
    private String name;
    public HashMap<String, LittleObject> symbolTableMap = new HashMap<>();
    public HashMap<String, SymbolTable> childSymbolTableMap = new HashMap<>();
    private ArrayList<String> childSymbolTableKeys = new ArrayList<>();
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

    public void addEntry(SymbolTable symbolTable){
        if(!childSymbolTableKeys.contains(symbolTable.name)) {
            childSymbolTableKeys.add(symbolTable.name);
            childSymbolTableMap.put(symbolTable.name, symbolTable);
        }
    }

    public void printTable(OutputStreamWriter outputStreamWriter, boolean hasNewLine) throws Exception {
        String keyAndTypeString, valueString;

        String symbolTableString = "Symbol table " + name;

        outputStreamWriter.write(symbolTableString);
        System.out.print(symbolTableString);

        if (keys.size() > 0) {
            outputStreamWriter.write("\n");
            System.out.println();
        }

        int keyCounter = 1;

        for (String key : keys) {
            keyAndTypeString = "name " + key + " type " + symbolTableMap.get(key).getType();
            outputStreamWriter.write(keyAndTypeString);
            System.out.print(keyAndTypeString);

            if (symbolTableMap.get(key).getType().equals(LittleObject.TYPE_STRING)) {
                valueString = " value " + symbolTableMap.get(key).getValue();
                outputStreamWriter.write(valueString);
                System.out.print(valueString);
            }

            if (keyCounter != keys.size()) {
                outputStreamWriter.write("\n");
                System.out.println();
            }
            keyCounter++;
        }

        if (hasNewLine) {
            outputStreamWriter.write("\n\n");
            System.out.println("\n");
        }
    }
}
