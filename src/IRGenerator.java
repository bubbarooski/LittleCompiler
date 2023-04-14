import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Stack;

public class IRGenerator {

    Stack<LittleObject> tokenStack = new Stack<>();
    ArrayList<String> irList = new ArrayList<>();
    int temporaryCount = 0;
    int parenthesesCount = 0;

    public IRGenerator(){

    }

    public void pushToken(LittleObject littleObject){
        if(littleObject.getValue().equals(";"))
            processTokenStack(false);
        else if(littleObject.getValue().equals(")"))
            processTokenStack(true);
        else
            tokenStack.add(littleObject);
    }

    private void processTokenStack(boolean forParentheses){
        LittleObject poppedToken1, infixToken, poppedToken2;
        if(forParentheses){
            do{
                poppedToken1 = tokenStack.pop();
                infixToken = tokenStack.pop();
                poppedToken2 = tokenStack.pop();
            } while (!poppedToken1.getValue().equals("("));
        } else {
            do{
                poppedToken1 = tokenStack.pop();
                infixToken = tokenStack.pop();
                poppedToken2 = tokenStack.pop();
            } while (!tokenStack.empty());
        }
    }
    public ArrayList<String> getIR(){
        return irList;
    }

    private IRCode generateSTOREI(String arg1, String arg2){
        return new IRCode("STOREI", arg1, arg2, null);
    }

    private IRCode generateMULTI(String arg1, String arg2, String arg3){
        return new IRCode("MULTI", arg1, arg2, arg3);
    }

    private IRCode generateDIVI(String arg1, String arg2, String arg3){
        return new IRCode("DIVI", arg1, arg2, arg3);
    }

    private IRCode generateADDI(String arg1, String arg2, String arg3){
        return new IRCode("ADDI", arg1, arg2, arg3);
    }

    private IRCode generateSUBI(String arg1, String arg2, String arg3){
        return new IRCode("SUBI", arg1, arg2, arg3);
    }

    private IRCode generateWRITEI(String arg1){
        return new IRCode("WRITEI", arg1, null, null);
    }

    private IRCode generateREADI(String arg1){
        return new IRCode("READI", arg1, null, null);
    }

    private IRCode generateRET(){
        return new IRCode("RET", null, null, null);
    }

}
