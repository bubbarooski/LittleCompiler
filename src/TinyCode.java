
public class TinyCode {
    public String func = "";
    public String arg1, arg2;
    
    private StringBuilder stringBuilder = new StringBuilder();

	public TinyCode(String func, String arg1, String arg2) {
		this.func = func;
		this.arg1 = arg1;
		this.arg2 = arg2;
	}
	
	@Override
	public String toString(){
	    stringBuilder.append(this.func);
	    if (this.arg1 != null) stringBuilder.append(" "+this.arg1);
	    if (this.arg2 != null) stringBuilder.append(" "+this.arg2);
	    return stringBuilder.toString();
	}
}
