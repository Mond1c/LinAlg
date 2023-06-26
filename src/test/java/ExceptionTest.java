import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.linalg.expression.parser.Parser;
import org.linalg.expression.parser.exceptions.ParserException;

public class ExceptionTest {
    private final static Parser PARSER = new Parser();

    @Test
    public void testUnexpectedToken() {
        RuntimeException exception = Assertions.assertThrows(ParserException.class, () -> PARSER.parse("tureiotueor"));
        Assertions.assertTrue(exception.getMessage().toLowerCase().contains("unexpected token"));
    }

    @Test
    public void testNoArgument() {
        RuntimeException exception = Assertions.assertThrows(ParserException.class, () -> PARSER.parse("12 + "));
        Assertions.assertTrue(exception.getMessage().toLowerCase().contains("no argument"));
    }

    @Test
    public void testNoClosingParenthesis() {
        RuntimeException exception = Assertions.assertThrows(ParserException.class, () -> PARSER.parse("(12 + 3"));
        Assertions.assertTrue(exception.getMessage().toLowerCase().contains("no closing parenthesis"));
    }

    @Test
    public void testNoOpenParenthesis() {
        RuntimeException exception = Assertions.assertThrows(ParserException.class, () -> PARSER.parse("12 + 3)"));
        Assertions.assertTrue(exception.getMessage().toLowerCase().contains("no opening parenthesis"));
    }

    @Test
    public void testSpacesInNumber() {
        RuntimeException exception = Assertions.assertThrows(ParserException.class, () -> PARSER.parse("1 2"));
        Assertions.assertTrue(exception.getMessage().toLowerCase().contains("spaces in number"));
    }

    @Test
    public void testMatrixBracketCount() {
        RuntimeException exception = Assertions.assertThrows(ParserException.class, () -> PARSER.parse("{{{"));
        Assertions.assertTrue(exception.getMessage().toLowerCase().contains("you can use only two {"));
    }

    @Test
    public void testMatrixZeroBrackets() {
        RuntimeException exception = Assertions.assertThrows(ParserException.class, () -> PARSER.parse("{"));
        Assertions.assertTrue(exception.getMessage().toLowerCase().contains("expected more }"));
    }
}
