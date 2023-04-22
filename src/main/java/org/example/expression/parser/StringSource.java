package org.example.expression.parser;

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

    public char back() {
        return data.charAt(pos--);
    }

    public void back(int n) {
        pos -= n;
    }

    public char cur() {
        return data.charAt(pos);
    }

    public IllegalArgumentException error(final String message) {
        return new IllegalArgumentException(pos + ": " + message);
    }
}
