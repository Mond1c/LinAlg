package org.linalg.expression.parser;

import org.linalg.expression.parser.exceptions.ParserException;

public class StringSource {
    private final String data;
    private int pos;

    public StringSource(final String data) {
        this.data = data;
    }

    public boolean hasNext() {
        return pos < data.length();
    }

    public char next() {
        return data.charAt(pos++);
    }


    public void back(int n) {
        pos -= n;
    }

    public RuntimeException error(final String message) {
        return new ParserException(data, pos, message);
    }
}
