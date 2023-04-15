import java.util.ArrayList;
import java.util.Stack;

public class IRCGenerator {

    Stack<LittleObject> tokenStack = new Stack<>();
    ArrayList<IRCode> ircList = new ArrayList<>();
    int temporaryCount = -1;
    int parenthesesCount = 0;

    public IRCGenerator(){

    }

    public void pushToken(LittleObject littleObject){
        if(littleObject.getValue().equals(";"))
            processTokenStack();
        else if(littleObject.getValue().equals(")"))
            processTokenStack();
        else
            tokenStack.add(littleObject);
    }

    private void processTokenStack(){
        boolean shouldContinue;
        do {
            shouldContinue = process();
        } while(shouldContinue);
    }

    private boolean process(){
        LittleObject token1, midToken, token2;
        String resultTemporary;
        if (tokenStack.empty()) return false; // if stack is empty then stop processing

        token2 = tokenStack.pop();
        midToken = tokenStack.pop();
        if(midToken.getValue().equals("(")) { // if a start parentheses is encountered that means we need to stop processing
            tokenStack.push(token2);
            return false;
        }
        token1 = tokenStack.pop();

        resultTemporary = availableTemp();

        // Generate code for arithmetic and assignment operations
        switch(midToken.getValue()){
            case ":=":

                if(token2.getType().equals(LittleObject.TYPE_TEMPORARY)){
                    generateSTOREI(token2.getId(), token1.getId());
                } else if(token1.getType().equals(LittleObject.TYPE_INT) || token2.getType().equals(LittleObject.TYPE_INT)) {
                    generateSTOREI(token2.getValue(), resultTemporary);
                    generateSTOREI(resultTemporary, token1.getId());
                } else {
                    generateSTOREF(token2.getValue(), resultTemporary);
                    generateSTOREF(resultTemporary, token1.getId());
                }
                tokenStack.push(new LittleObject(resultTemporary, null));
                break;

            case "*":

                if(token1.getType().equals(LittleObject.TYPE_INT) || token2.getType().equals(LittleObject.TYPE_INT)) {
                    generateMULTI(token1.getId(), token2.getId(), resultTemporary);
                } else {
                    generateMULTF(token1.getId(), token2.getId(), resultTemporary);
                }
                tokenStack.push(new LittleObject(resultTemporary, null));
                break;

            case "/":

                if(token1.getType().equals(LittleObject.TYPE_INT) || token2.getType().equals(LittleObject.TYPE_INT)) {
                    generateDIVI(token1.getId(), token2.getId(), resultTemporary);
                } else {
                    generateDIVF(token1.getId(), token2.getId(), resultTemporary);
                }
                tokenStack.push(new LittleObject(resultTemporary, null));
                break;

            case "+":

                if(token1.getType().equals(LittleObject.TYPE_INT) || token2.getType().equals(LittleObject.TYPE_INT)) {
                    generateADDI(token1.getId(), token2.getId(), resultTemporary);
                } else {
                    generateADDF(token1.getId(), token2.getId(), resultTemporary);
                }
                tokenStack.push(new LittleObject(resultTemporary, null));
                break;

            case "-":

                if(token1.getType().equals(LittleObject.TYPE_INT) || token2.getType().equals(LittleObject.TYPE_INT)) {
                    generateSUBI(token1.getId(), token2.getId(), resultTemporary);
                } else {
                    generateSUBF(token1.getId(), token2.getId(), resultTemporary);
                }
                tokenStack.push(new LittleObject(resultTemporary, null));
                break;
        }

        // Generate code for read/write functions
        if(token1.getId().equals("WRITE")){
            generateWRITEI(token2.getId());
        } else if(token1.getId().equals("READ")){
            generateREADI(token2.getId());
        }

        return true;
    }
    public ArrayList<IRCode> getIR(){
        return ircList;
    }

    private void generateSTOREI(String arg1, String arg2){
        ircList.add(new IRCode("STOREI", arg1, arg2, null));
    }

    private void generateMULTI(String arg1, String arg2, String arg3){
        ircList.add(new IRCode("MULTI", arg1, arg2, arg3));
    }

    private void generateDIVI(String arg1, String arg2, String arg3){
        ircList.add(new IRCode("DIVI", arg1, arg2, arg3));
    }

    private void generateADDI(String arg1, String arg2, String arg3){
        ircList.add(new IRCode("ADDI", arg1, arg2, arg3));
    }

    private void generateSUBI(String arg1, String arg2, String arg3){
        ircList.add(new IRCode("SUBI", arg1, arg2, arg3));
    }

    private void generateWRITEI(String arg1){
        ircList.add(new IRCode("WRITEI", arg1, null, null));
    }

    private void generateREADI(String arg1){
        ircList.add(new IRCode("READI", arg1, null, null));
    }

    private void generateSTOREF(String arg1, String arg2){
        ircList.add(new IRCode("STOREF", arg1, arg2, null));
    }

    private void generateMULTF(String arg1, String arg2, String arg3){
        ircList.add(new IRCode("MULTF", arg1, arg2, arg3));
    }

    private void generateDIVF(String arg1, String arg2, String arg3){
        ircList.add(new IRCode("DIVF", arg1, arg2, arg3));
    }

    private void generateADDF(String arg1, String arg2, String arg3){
        ircList.add(new IRCode("ADDF", arg1, arg2, arg3));
    }

    private void generateSUBF(String arg1, String arg2, String arg3){
        ircList.add(new IRCode("SUBF", arg1, arg2, arg3));
    }

    private void generateWRITEF(String arg1){
        ircList.add(new IRCode("WRITEF", arg1, null, null));
    }

    private void generateREADF(String arg1){
        ircList.add(new IRCode("READF", arg1, null, null));
    }

    public void generateLINK(){
        ircList.add(new IRCode("LINK", null, null, null));
    }
    public void generateLABEL(String namespace){
        ircList.add(new IRCode("LINK", namespace, null, null));
    }
    public void generateRET(){
        ircList.add(new IRCode("RET", null, null, null));
    }

    private String availableTemp(){
        temporaryCount++;
        return "T"+temporaryCount;
    }

    private String getLastTemp(){
        temporaryCount--;
        String temporary = "T"+temporaryCount;
        temporaryCount++;
        return temporary;
    }

}
