package org.linalg.expression.parser.exceptions;

public class ParserException extends RuntimeException {
    public ParserException(String expression, int pos, String message) {
        super(message + ": " + expression.substring(Math.max(0, pos - 11), pos - 1)
                + " --> " + expression.charAt(pos - 1) + " <-- "
                + (pos < expression.length() ? expression.substring(pos, Math.min(pos + 10, expression.length())) : ""));
    }
}
