import java.util.List;

public class IRCodeListToArray {
	
    public static IRCode[] convertIRCodeListToArray(List<IRCode> irCodeList) {
        IRCode[] irCodeArray = new IRCode[irCodeList.size()]; // create a new array with the same size as the list
        for (int i = 0; i < irCodeList.size(); i++) {
            irCodeArray[i] = irCodeList.get(i); // copy each element of the list to the array
        }
        return irCodeArray; // return the array
    }
}