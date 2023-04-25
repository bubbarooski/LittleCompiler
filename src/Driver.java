import java.io.IOException;
import java.util.Arrays;

import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.*;
public class Driver {
    public static void main(String[] args) throws ParseCancellationException {

        try {
            @SuppressWarnings("deprecation")
            ANTLRInputStream input = new ANTLRInputStream(System.in);

            LittleLexer lexer = new LittleLexer(input);
            lexer.removeErrorListeners();
            lexer.addErrorListener(ErrorDetector.ed);

            CommonTokenStream tokens = new CommonTokenStream(lexer);
            tokens.fill();

            LittleParser parser = new LittleParser(tokens);
            parser.removeErrorListeners();
            parser.addErrorListener(ErrorDetector.ed);

            ParseTree tree = parser.program();

            SymbolTableBuilder symbolTableBuilder = new SymbolTableBuilder();

            ParseTreeWalker walker = new ParseTreeWalker();
            walker.walk(symbolTableBuilder, tree);

            IRCode[] irCodeArray = symbolTableBuilder.getIRCode(); // Shane: Iterate through this list and generate Tiny Assembly Code for each IR code
            
            System.out.println();
            
            TinyGenerator assembler = new TinyGenerator();
      
            
            assembler.generateAssembly(irCodeArray); 
            

            
   			/*for (int i = 0; i < tokens.size()-1; i++) {
   				System.out.println("Token Type: " + getTypeString(tokens.get(i).getType()));
   	   			System.out.println("Value: " + tokens.get(i).getText());
   			}*/

            //System.out.println("Accepted");

        } catch (ParseCancellationException e) {
            //e.printStackTrace();
            //System.out.println("Not accepted");
            System.exit(0);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }
    }

    public static String getTypeString(int typeNum) {
        String typeStr = null;

        switch (typeNum) {
            case 1 : typeStr = "KEYWORD";
                break;
            case 2 : typeStr = "OPERATOR";
                break;
            case 3 : typeStr = "STRINGLITERAL";
                break;
            case 4 : typeStr = "INTLITERAL";
                break;
            case 5 : typeStr = "IDENTIFIER";
                break;
            case 6 : typeStr = "FLOAT";
                break;
            case 7 : typeStr = "COMMENT";
                break;
            case 8 : typeStr = "NEWLINE";
                break;
            case 9 : typeStr = "WHITESPACE";
                break;
            default : typeStr = "Unknown";
        }

        return typeStr;
    }
}
