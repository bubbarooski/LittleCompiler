import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class IRGenerator {

    LinkedList<LittleObject> tokenList = new LinkedList<>();
    ArrayList<String> irList = new ArrayList<>();
    ArrayList<Integer> parenthesesLocations = new ArrayList<>();
    ArrayList<Integer> multiplicationLocations = new ArrayList<>();
    ArrayList<Integer> divisionLocations = new ArrayList<>();
    ArrayList<Integer> additionLocations = new ArrayList<>();
    ArrayList<Integer> subtractionLocations = new ArrayList<>();

    int temporaryCount = 0;

    public IRGenerator(){

    }

    public void pushToken(LittleObject littleObject){
        if(littleObject.getValue().equals(";")){
            processTokenList(0, tokenList.size()-1);
        } else {
            checkForFlag(littleObject.getValue());
            tokenList.add(littleObject);
        }
    }

    private void processTokenList(int start, int end){
        String token;

        for(int i = start; i < end; i++){
            token = tokenList.get(i).getValue();
        }

    }
    public ArrayList<String> getIR(){
        return irList;
    }

    private void checkForFlag(String token){
        if(token.equals("(")) parenthesesLocations.add(tokenList.size());
        else if(token.equals(")")) parenthesesLocations.add(tokenList.size());
        else if(token.equals("*")) multiplicationLocations.add(tokenList.size());
        else if(token.equals("/")) divisionLocations.add(tokenList.size());
        else if(token.equals("+")) additionLocations.add(tokenList.size());
        else if(token.equals("-")) subtractionLocations.add(tokenList.size());
    }

    private String generateSTOREI(String i){
        return "STOREI " + i + " $T"+temporaryCount;
    }

    private String generateMULTI(String i1, String i2){
        return "MULTI " + i1 + " " + i2 + " $T"+temporaryCount;
    }

}
