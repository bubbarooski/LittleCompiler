import java.util.ArrayList;
import java.util.List;

public class TinyOptimizer {
	
	public List<String> littleLang = new ArrayList<String>();
	public List<IRCode> oirc = new ArrayList<IRCode>();
	
	public IRCode temp;
	
	public TinyOptimizer(IRCode[] irc) {
		
		
		
		for (int i = 0; i < irc.length; i++) {
			
			if (irc[i].func.compareTo("STOREI") == 0 && irc[i + 1].func.compareTo("STOREI") == 0) {
			
				if (irc[i].arg2.compareTo(irc[i + 1].arg1) == 0) {
					temp = new IRCode("STOREI", irc[i].arg1, irc[i+1].arg2, null);
					oirc.add(temp);
					i++;
					continue;
				}
			}
			
			if (irc[i].func.compareTo("STOREF") == 0 && irc[i + 1].func.compareTo("STOREF") == 0) {
				
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
				temp = new IRCode("MULTI", irc[i].arg1, irc[i].arg2, "$T0");
				oirc.add(temp);
				temp = new IRCode("STOREI", "$T0", irc[i+1].arg2, null);
				oirc.add(temp);
				i++;
				continue;
			}
			
			if (irc[i].func.compareTo("MULTF") == 0) {
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
			
			if (irc[i].func.compareTo("DIVF") == 0) {
				temp = new IRCode("DIVF", irc[i].arg1, irc[i].arg2, "$T0");
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
	
}
