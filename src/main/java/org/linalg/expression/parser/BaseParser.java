package org.linalg.expression.parser;

public class BaseParser {
    private static final char END = '\0';
    private StringSource source;
    protected char ch = 0xffff;

    protected BaseParser() {

    }

    protected void setSource(final StringSource source) {
        this.source = source;
        take();
    }

    protected char take() {
        final char result = ch;
        ch = source.hasNext() ? source.next() : END;
        return result;
    }

    protected boolean test(final char expected) {
        return ch == expected;
    }

    protected boolean take(final char expected) {
        if (test(expected)) {
            take();
            return true;
        }
        return false;
    }

    protected boolean take(final String expected) {
        char c = ch;
        for (int i = 0; i < expected.length(); i++) {
            if (!take(expected.charAt(i))) {
                source.back(i);
                ch = c;
                return false;
            }
        }
        return true;
    }

    protected boolean eof() {
        return take(END);
    }

    protected void skipWhitespaces() {
        while (Character.isWhitespace(ch)) {
            take();
        }
    }

    protected RuntimeException error(final String message) {
        return source.error(message);
    }

    protected boolean between(final char from, final char to) {
        return from <= ch && ch <= to;
    }
}
