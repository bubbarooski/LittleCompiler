public class IRCode {
    public String func = "";
    public String arg1, arg2, arg3;

    public IRCode(String func, String arg1, String arg2, String arg3){
        this.func = func;   //opcode
        this.arg1 = arg1;
        this.arg2 = arg2;
        this.arg3 = arg3;
    }

    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.func);
        if (this.arg1 != null) stringBuilder.append(" "+this.arg1);
        if (this.arg2 != null) stringBuilder.append(" "+this.arg2);
        if (this.arg3 != null) stringBuilder.append(" "+this.arg3);
        return stringBuilder.toString();
    }
}
