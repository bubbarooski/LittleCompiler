import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.misc.ParseCancellationException;

public class ErrorDetector extends BaseErrorListener {
    public static final ErrorDetector ed = new ErrorDetector();

    @Override
    public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, // continues on next line
                            int charPositionInLine, String msg, RecognitionException e) throws ParseCancellationException {
        //{
        throw new ParseCancellationException("line " + line + ":" + charPositionInLine + " " + msg);


    }

}