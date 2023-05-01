import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TinyGenerator {
	
	public List<String> variables = new ArrayList<String>();
	List<String> stringValue = new ArrayList<String>();
	int registerCounter = 0;
	public List<TinyCode> otc = new ArrayList<TinyCode>();
	TinyCode temp;
	
	/*
	 * Driver function for the entire assembly writing process
	 */	
	
	public void generateAssembly(IRCode[] array){
		printTiny(array);
		
		for(IRCode irCode : array){            	
			variables(irCode);
        }
		
		printVariables();
		
		for(IRCode irCode : array){            	
        	writeAssembly(irCode);
        }
		
		for (int i = 0; i < otc.size(); i++) {
			if (otc.get(i).func.compareTo("move") == 0 && otc.get(i+1).func.compareTo("move") == 0) {
				if (otc.get(i).arg1.compareTo(otc.get(i+1).arg2) == 0 && otc.get(i).arg2.compareTo(otc.get(i+1).arg1) == 0) {
					otc.remove(i+1);
				}
			}
		}
		
		for (TinyCode tc: otc) {
			System.out.print(tc.func);
			
			if (tc.arg1 != null) {
				System.out.print(" " + tc.arg1);
			}
			if (tc.arg2 != null) {
				System.out.print(" " + tc.arg2);
			}
			
			System.out.println();
		}
	}
	
	
	
	/*
	 * Used to find the variables for the Tiny program
	 */	
	
	public void variables(IRCode code) {
		
		//System.out.println(code.func);
		
		if(code.func == "STORES") {
			//variables.add(code.arg1);
			//System.out.printf("str " + code.arg1 + " " + code.arg2 + "\n");
			temp = new TinyCode("str", code.arg1, code.arg2);
			otc.add(temp);

		}	
		
		if(code.func == "STOREI") {
			if(code.arg1.startsWith("$") == true) {
				//System.out.println(code.arg2);
				variables.add(code.arg2);
				//temp = new TinyCode("var", code.arg2, null);	/////
				//otc.add(temp);
			} else if (isInteger(code.arg1)) {
				variables.add(code.arg2);
				//temp = new TinyCode("var", code.arg2, null);	/////
				//otc.add(temp);
			}
		}
		
		if(code.func == "READI") {
			//System.out.println(code.arg1);
			if(variables.contains(code.arg1)) {
				return;
			}
			else{
				variables.add(code.arg1);
				//temp = new TinyCode("var", code.arg1, null);
				//otc.add(temp);
			}
		}
		
		if(code.func == "WRITEI") {
			//System.out.println(code.arg1);
			if(variables.contains(code.arg1)) {
				return;
			}
			else{
				variables.add(code.arg1);
				
			}
		}

		if(code.func == "STOREF") {
			if(code.arg1.startsWith("$") == true) {
				//System.out.println(code.arg2);
				variables.add(code.arg2);
				//temp = new TinyCode("var", code.arg2, null);    /////
				//otc.add(temp);
			} else if (!isInteger(code.arg1)) {
				variables.add(code.arg2);
				//temp = new TinyCode("var", code.arg2, null);    /////
				//otc.add(temp);
			}
		}
		
		if(code.func == "READF") {
			//System.out.println(code.arg1);
			if(variables.contains(code.arg1)) {
				return;
			}
			else{
				variables.add(code.arg1);
			}
		}
		
		if(code.func == "WRITEF") {
			//System.out.println(code.arg1);
			if(variables.contains(code.arg1)) {
				return;
			}
			else{
				variables.add(code.arg1);
			}
		}
		
	}
	
	
	
	/*
	 *  Used to print the variables from the variables list
	 */
	
	public void printVariables() {
				
		// Deletes any duplicates and reorders list of variables - could probably just use a list to begin with but too deep now
		Set<String> set = new HashSet<>(variables);
		variables.clear();
		variables.addAll(set);
		
		for(int i = 0; i<variables.size(); i++) {
			
			// For testing
			//System.out.println(variables.get(i) + "1");
			
			if(variables.get(i).equals("newline") == true) {
				//System.out.printf("str %s \"\\n\"\n", variables.get(i));
				temp = new TinyCode("str", variables.get(i), "\"\\n\"");
				otc.add(temp);
			}
			

			//System.out.printf("var %s\n", variables.get(i));
			temp = new TinyCode("var", variables.get(i), null);
			otc.add(temp);

		}
		
		return;
	}
	
	
	
	/*
	 * Used to write assembly lines for a given IRCode
	 */
	
	public void writeAssembly(IRCode code) {
		
		/*
		 * This chunk of code renames any use of registers to a proper format
		 */
		
		if(code.arg1 == null) {}
		else {
			if(code.arg1.startsWith("$") == true) {
				code.arg1 = "r" + code.arg1.substring(2, code.arg1.length());
			}
		}
		
		if(code.arg2 == null) {}
		else {
			if(code.arg2.startsWith("$") == true) {
				code.arg2 = "r" + code.arg2.substring(2, code.arg2.length());
			}
		}
		
		if(code.arg3 == null) {}
		else {
			if(code.arg3.startsWith("$") == true) {
				code.arg3 = "r" + code.arg3.substring(2, code.arg3.length());
			}
		}
		
		/*
		 * This chunk of code determine the appropriate assembly to write
		 */
		
		if(code.func.equals("STOREI") == true) {
			//System.out.printf("move" + " " + code.arg1 + " " + code.arg2 + "\n");
			temp = new TinyCode("move", code.arg1, code.arg2);
			otc.add(temp);
		}
		
		if(code.func.equals("STOREF") == true) {
			//System.out.printf("move" + " " + code.arg1 + " " + code.arg2 + "\n");
			temp = new TinyCode("move", code.arg1, code.arg2);
			otc.add(temp);
		}
		
		if(code.func.equals("READI") == true) {
			//System.out.printf("sys readi %s\n", code.arg1);
			temp = new TinyCode("sys readi", code.arg1, null);
			otc.add(temp);
		}
		
		if(code.func.equals("READF") == true) {
			//System.out.printf("sys readf %s\n", code.arg1);
			temp = new TinyCode("sys readr", code.arg1, null);
			otc.add(temp);
		}
		
		if(code.func.equals("WRITEI") == true) {
			//System.out.printf("sys writei %s\n", code.arg1);
			temp = new TinyCode("sys writei", code.arg1, null);
			otc.add(temp);
		}
		
		if(code.func.equals("WRITEF") == true) {
			//System.out.printf("sys writer %s\n", code.arg1);
			temp = new TinyCode("sys writer", code.arg1, null);
			otc.add(temp);
		}
		
		if(code.func.equals("WRITES") == true) {
			//System.out.printf("sys writes %s\n", code.arg1);
			temp = new TinyCode("sys writes", code.arg1, null);
			otc.add(temp);
		}
		
		if(code.func.equals("ADDI") == true) {
			//System.out.printf("move %s %s\n", code.arg1, code.arg3);
			temp = new TinyCode("move", code.arg1, code.arg3);
			otc.add(temp);
			
			//System.out.printf("addi %s %s\n", code.arg2, code.arg3);
			temp = new TinyCode("addi", code.arg2, code.arg3);
			otc.add(temp);
		}
		
		if(code.func.equals("ADDF") == true) {
			//System.out.printf("move %s %s\n", code.arg1, code.arg3);
			temp = new TinyCode("move", code.arg1, code.arg3);
			otc.add(temp);
			
			//System.out.printf("addr %s %s\n", code.arg2, code.arg3);
			temp = new TinyCode("addr", code.arg2, code.arg3);
			otc.add(temp);
		}
		
		if(code.func.equals("SUBI") == true) {
			//System.out.printf("move %s %s\n", code.arg1, code.arg3);
			temp = new TinyCode("move", code.arg1, code.arg3);
			otc.add(temp);
			
			//System.out.printf("subi %s %s\n", code.arg2, code.arg3);
			temp = new TinyCode("subi", code.arg2, code.arg3);
			otc.add(temp);
		}
		
		if(code.func.equals("SUBF") == true) {
			//System.out.printf("move %s %s\n", code.arg1, code.arg3);
			temp = new TinyCode("move", code.arg1, code.arg3);
			otc.add(temp);
			
			//System.out.printf("subr %s %s\n", code.arg2, code.arg3);
			temp = new TinyCode("subr", code.arg2, code.arg3);
			otc.add(temp);
		}
		
		if(code.func.equals("MULTI") == true) {
			//System.out.printf("move %s %s\n", code.arg1, code.arg3);
			temp = new TinyCode("move", code.arg1, code.arg3);
			otc.add(temp);
			
			//System.out.printf("muli %s %s\n", code.arg2, code.arg3);
			temp = new TinyCode("muli", code.arg2, code.arg3);
			otc.add(temp);
		}
		
		if(code.func.equals("MULTF") == true) {
			//System.out.printf("move %s %s\n", code.arg1, code.arg3);
			temp = new TinyCode("move", code.arg1, code.arg3);
			otc.add(temp);
			
			//System.out.printf("mulr %s %s\n", code.arg2, code.arg3);
			temp = new TinyCode("mulr", code.arg2, code.arg3);
			otc.add(temp);
		}
		
		if(code.func.equals("DIVI") == true) {
			//System.out.printf("move %s %s\n", code.arg1, code.arg3);
			temp = new TinyCode("move", code.arg1, code.arg3);
			otc.add(temp);
			
			//System.out.printf("divi %s %s\n", code.arg2, code.arg3);
			temp = new TinyCode("divi", code.arg2, code.arg3);
			otc.add(temp);
		}
		
		if(code.func.equals("DIVF") == true) {
			//System.out.printf("move %s %s\n", code.arg1, code.arg3);
			temp = new TinyCode("move", code.arg1, code.arg3);
			otc.add(temp);
			
			//System.out.printf("divr %s %s\n", code.arg2, code.arg3);
			temp = new TinyCode("divr", code.arg2, code.arg3);
			otc.add(temp);
		}
		
		if(code.func.equals("RET") == true) {
			//System.out.printf("sys halt\n");
			temp = new TinyCode("sys halt", null, null);
			otc.add(temp);
		}
	}
	
	
	
	public void printTiny(IRCode[] array) {
		// Printing tiny code
        System.out.println(";IR code");
		for(IRCode irCode : array){            	
        	System.out.printf(";%s\n", irCode);
        }
        System.out.println(";tiny code");
	}
	
	public static boolean isInteger(String str) {
	    try {
	        Integer.parseInt(str);
	        return true;
	    } catch (NumberFormatException e) {
	        return false;
	    }
	}
}
