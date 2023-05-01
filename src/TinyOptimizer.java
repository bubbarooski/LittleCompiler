import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TinyOptimizer {
	
	public List<String> littleLang = new ArrayList<String>();
	public List<IRCode> oirc = new ArrayList<IRCode>();
	HashMap<String, Integer> oneMap = new HashMap<>();
	HashMap<String, Float> onefMap = new HashMap<>();
	
	public IRCode temp;
	
	public TinyOptimizer(IRCode[] irc) {

		for (int i = 0; i < irc.length; i++) {
			
			if (irc[i].func.compareTo("STOREI") == 0 && irc[i + 1].func.compareTo("STOREI") == 0) {
			
				if (isInteger(irc[i].arg1) && Integer.parseInt(irc[i].arg1) == 1) {
					oneMap.put(irc[i+1].arg2, 1);
					System.out.println(irc[i].arg2);
					
				}
				
				if (irc[i].arg2.compareTo(irc[i + 1].arg1) == 0) {
					temp = new IRCode("STOREI", irc[i].arg1, irc[i+1].arg2, null);
					oirc.add(temp);
					i++;
					continue;
				}
			}
			
			/////////
			
			if (irc[i].func.compareTo("STOREF") == 0 && irc[i + 1].func.compareTo("STOREF") == 0) {
				
				if (isFloat(irc[i].arg1) && Float.parseFloat(irc[i].arg1) == 1) {
					onefMap.put(irc[i+1].arg2, 1.0f);
					System.out.println(irc[i].arg2);
					
				}
				
				if (irc[i].arg2.compareTo(irc[i + 1].arg1) == 0) {
					temp = new IRCode("STOREF", irc[i].arg1, irc[i+1].arg2, null);
					oirc.add(temp);
					i++;
					continue;
				}
			}
			
			if (irc[i].func.compareTo("SUBI") == 0) {
				temp = new IRCode("SUBI", irc[i].arg1, irc[i].arg2, "$T0");
				oirc.add(temp);
				temp = new IRCode("STOREI", "$T0", irc[i+1].arg2, null);
				oirc.add(temp);
				i++;
				continue;
			}
			
			if (irc[i].func.compareTo("MULTI") == 0) {
				
				if (oneMap.containsKey(irc[i].arg1) ) {
					temp = new IRCode("STOREI", irc[i].arg2, irc[i].arg3, null);
					oirc.add(temp);
					continue;
					
				} else if (oneMap.containsKey(irc[i].arg2)) {
					temp = new IRCode("STOREI", irc[i].arg1, irc[i].arg3, null);
					oirc.add(temp);
					continue;
				}
				
				temp = new IRCode("MULTI", irc[i].arg1, irc[i].arg2, "$T0");
				oirc.add(temp);
				temp = new IRCode("STOREI", "$T0", irc[i+1].arg2, null);
				oirc.add(temp);
				i++;
				continue;
			}
			
			if (irc[i].func.compareTo("MULTF") == 0) {
				if (onefMap.containsKey(irc[i].arg1) ) {
					temp = new IRCode("STOREF", irc[i].arg2, irc[i].arg3, null);
					oirc.add(temp);
					continue;
					
				} else if (onefMap.containsKey(irc[i].arg2)) {
					temp = new IRCode("STOREF", irc[i].arg1, irc[i].arg3, null);
					oirc.add(temp);
					continue;
				}
				
				temp = new IRCode("MULTF", irc[i].arg1, irc[i].arg2, "$T0");
				oirc.add(temp);
				temp = new IRCode("STOREI", "$T0", irc[i+1].arg2, null);
				oirc.add(temp);
				i++;
				continue;
			}
			
			if (irc[i].func.compareTo("ADDI") == 0) {
				temp = new IRCode("ADDI", irc[i].arg1, irc[i].arg2, "$T0");
				oirc.add(temp);
				temp = new IRCode("STOREI", "$T0", irc[i+1].arg2, null);
				oirc.add(temp);
				i++;
				continue;
			}
			
			if (irc[i].func.compareTo("DIVI") == 0) {
				temp = new IRCode("DIVI", irc[i].arg1, irc[i].arg2, "$T0");
				oirc.add(temp);
				temp = new IRCode("STOREI", "$T0", irc[i+1].arg2, null);
				oirc.add(temp);
				i++;
				continue;
			}
			
			if (irc[i].func.compareTo("DIVR") == 0) {
				temp = new IRCode("DIVR", irc[i].arg1, irc[i].arg2, "$T0");
				oirc.add(temp);
				temp = new IRCode("STOREI", "$T0", irc[i+1].arg2, null);
				oirc.add(temp);
				i++;
				continue;
			}
			
			
			temp = irc[i];
			oirc.add(temp);
			
			
		}
		
		
	}
	
	public static boolean isInteger(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
	public static boolean isFloat(String str) {
	    try {
	        Float.parseFloat(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
	
}
