import java.util.ArrayList;
import java.util.Stack;
import java.util.regex.Pattern;

public class IRCGenerator {

    Stack<LittleObject> tokenStack = new Stack<>();
    ArrayList<IRCode> ircDeclareList = new ArrayList<>();
    ArrayList<IRCode> ircList = new ArrayList<>();
    int temporaryCount = 1;

    Pattern floatLitPattern, intLiteralPattern;

    public IRCGenerator(){
        floatLitPattern = Pattern.compile("\\d*\\.\\d");
        intLiteralPattern = Pattern.compile("\\d*");
    }

    public void pushToken(LittleObject littleObject) {
        if (littleObject.getId().equals(";"))
            processTokenStack();
        else if (littleObject.getId().equals(")"))
            processTokenStack();
        else {
            System.out.println(littleObject);
            tokenStack.add(littleObject);
        }
    }

    private void processTokenStack(){
        boolean shouldContinue;
        do {
            shouldContinue = process();
        } while(shouldContinue);
    }

    private boolean process(){
        LittleObject literalToken1, literalToken2;
        LittleObject token1, midToken, token2;
        String resultTemporary;
        String resultTempType;
        LittleObject[] tokenStackBuffer = new LittleObject[3];
        if (tokenStack.empty()) return false; // if stack is empty then stop processing

        token2 = tokenStack.pop();
        midToken = tokenStack.pop();
        if(midToken.getId().equals("(")) { // if a start parentheses is encountered that means we need to stop processing
            tokenStack.push(token2);
            return false;
        }
        token1 = tokenStack.pop();

        // Generate code for arithmetic and assignment operations
        switch(midToken.getId()){

            case ":=":

                if(tokenStack.empty()){
                    if(token2.getType().equals(LittleObject.TYPE_INT)) {
                        generateSTOREI(token2.getId(), nextAvailableTemp());
                        generateSTOREI(getLastTemp(), token1.getId());
                        break;
                    } else if (token2.getType().equals(LittleObject.TYPE_FLOAT)) {
                        generateSTOREF(token2.getId(), nextAvailableTemp());
                        generateSTOREF(getLastTemp(), token1.getId());
                        break;
                    }
                }

                if (token2.getType().equals(LittleObject.TYPE_STRING))
                    generateSTORES(token1.getId(), token2.getId());
                else if(token2.getType().equals(LittleObject.TYPE_INT) ||
                        token2.getType().equals(LittleObject.TYPE_TEMPORARY_INT)) {
                    generateSTOREI(token2.getId(), token1.getId());
                } else {
                    generateSTOREF(token2.getId(), token1.getId());
                }
                break;

            case "*":

                if (popInBufferArray(checkForLiteralInTemporary(token1),
                        checkForLiteralInTemporary(token2),
                        token1, midToken, token2)) break;

                resultTemporary = nextAvailableTemp();
                if(token1.getType().equals(LittleObject.TYPE_INT) || token2.getType().equals(LittleObject.TYPE_INT)) {
                    generateMULTI(token1.getId(), token2.getId(), resultTemporary);
                    resultTempType = LittleObject.TYPE_TEMPORARY_INT;
                } else {
                    generateMULTF(token1.getId(), token2.getId(), resultTemporary);
                    resultTempType = LittleObject.TYPE_TEMPORARY_FLOAT;
                }
                tokenStack.push(new LittleObject(resultTemporary, resultTempType, null));
                break;

            case "/":

                if (popInBufferArray(checkForLiteralInTemporary(token1),
                        checkForLiteralInTemporary(token2),
                        token1, midToken, token2)) break;

                resultTemporary = nextAvailableTemp();
                if(token1.getType().equals(LittleObject.TYPE_INT) || token2.getType().equals(LittleObject.TYPE_INT)) {
                    generateDIVI(token1.getId(), token2.getId(), resultTemporary);
                    resultTempType = LittleObject.TYPE_TEMPORARY_INT;
                } else {
                    generateDIVF(token1.getId(), token2.getId(), resultTemporary);
                    resultTempType = LittleObject.TYPE_TEMPORARY_FLOAT;
                }
                tokenStack.push(new LittleObject(resultTemporary, resultTempType, null));
                break;

            case "+":

                if (popInBufferArray(checkForLiteralInTemporary(token1),
                        checkForLiteralInTemporary(token2),
                        token1, midToken, token2)) break;

                resultTemporary = nextAvailableTemp();
                if(token1.getType().equals(LittleObject.TYPE_INT) || token2.getType().equals(LittleObject.TYPE_INT)) {
                    generateADDI(token1.getId(), token2.getId(), resultTemporary);
                    resultTempType = LittleObject.TYPE_TEMPORARY_INT;
                } else {
                    generateADDF(token1.getId(), token2.getId(), resultTemporary);
                    resultTempType = LittleObject.TYPE_TEMPORARY_FLOAT;
                }
                tokenStack.push(new LittleObject(resultTemporary, resultTempType, null));
                break;

            case "-":

                if (popInBufferArray(checkForLiteralInTemporary(token1),
                        checkForLiteralInTemporary(token2),
                        token1, midToken, token2)) break;

                resultTemporary = nextAvailableTemp();
                if(token1.getType().equals(LittleObject.TYPE_INT) || token2.getType().equals(LittleObject.TYPE_INT)) {
                    generateSUBI(token1.getId(), token2.getId(), resultTemporary);
                    resultTempType = LittleObject.TYPE_TEMPORARY_INT;
                } else {
                    generateSUBF(token1.getId(), token2.getId(), resultTemporary);
                    resultTempType = LittleObject.TYPE_TEMPORARY_FLOAT;
                }
                tokenStack.push(new LittleObject(resultTemporary, resultTempType, null));
                break;
        }

        return true;
    }
    public ArrayList<IRCode> getIRC(){
        return ircList;
    }

    public ArrayList<IRCode> getDeclareIRC(){
        return ircDeclareList;
    }

    private void generateSTORES(String arg1, String arg2){
        ircList.add(new IRCode("STORES", arg1, arg2, null));
    }
    private void generateSTOREI(String arg1, String arg2){
        ircList.add(new IRCode("STOREI", arg1, arg2, null));
    }

    public void generateWRITES(String arg1){
        ircList.add(new IRCode("WRITES", arg1, null, null));
    }

    public void generateREADS(String arg1){
        ircList.add(new IRCode("READS", arg1, null, null));
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

    public void generateWRITEF(String arg1){
        ircList.add(new IRCode("WRITEF", arg1, null, null));
    }

    public void generateREADF(String arg1){
        ircList.add(new IRCode("READF", arg1, null, null));
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

    public void generateWRITEI(String arg1){
        ircList.add(new IRCode("WRITEI", arg1, null, null));
    }

    public void generateREADI(String arg1){
        ircList.add(new IRCode("READI", arg1, null, null));
    }

    public void generateLINK(){
        ircList.add(new IRCode("LINK", null, null, null));
    }
    public void generateLABEL(String namespace){
        ircList.add(new IRCode("LABEL", namespace, null, null));
    }
    public void generateRET(){
        ircList.add(new IRCode("RET", null, null, null));
    }

    private String nextAvailableTemp(){
        String temporary = "$T"+temporaryCount;
        temporaryCount++;
        return temporary;
    }

    private String getLastTemp(){
        temporaryCount--;
        String temporary = "$T"+temporaryCount;
        temporaryCount++;
        return temporary;
    }

    public void determineWrite(String id, SymbolTable currentSymbolTable){
        if(currentSymbolTable.lookupType(id).equals(LittleObject.TYPE_INT)) {
            generateWRITEI(id);
        } else if (currentSymbolTable.lookupType(id).equals(LittleObject.TYPE_FLOAT)) {
            generateWRITEF(id);
        } else if (currentSymbolTable.lookupType(id).equals(LittleObject.TYPE_STRING)){
            generateWRITES(id);
        }
    }

    public void determineRead(String id, SymbolTable currentSymbolTable) {
        if (currentSymbolTable.lookupType(id).equals(LittleObject.TYPE_INT)) {
            generateREADI(id);
        } else if (currentSymbolTable.lookupType(id).equals(LittleObject.TYPE_FLOAT)) {
            generateREADF(id);
        } else if (currentSymbolTable.lookupType(id).equals(LittleObject.TYPE_STRING)) {
            generateREADS(id);
        }
    }

    private LittleObject checkForLiteralInTemporary(LittleObject littleObject) {

        if((floatLitPattern.matcher(littleObject.getId()).matches() || floatLitPattern.matcher(littleObject.getId()).matches())){
            if (littleObject.getType().equals(LittleObject.TYPE_INT)) {
                generateSTOREI(littleObject.getId(), nextAvailableTemp());
                return new LittleObject(getLastTemp(), LittleObject.TYPE_TEMPORARY_INT, null);
            } else if (littleObject.getType().equals(LittleObject.TYPE_FLOAT)) {
                generateSTOREF(littleObject.getId(), nextAvailableTemp());
                return new LittleObject(getLastTemp(), LittleObject.TYPE_TEMPORARY_FLOAT, null);
            }
        }

        return null;
    }

    private boolean popInBufferArray(LittleObject nt1, LittleObject nt2, LittleObject t1,
                                     LittleObject mt, LittleObject t2) {

        if(nt1 == null && nt2 == null){
            return false;
        }

        if (nt1 != null) {
            tokenStack.push(nt1);
        } else {
            tokenStack.push(t1);
        }

        tokenStack.push(mt);

        if (nt2 != null) {
            tokenStack.push(nt2);
        } else {
            tokenStack.push(t2);
        }

        return true;
    }

}
